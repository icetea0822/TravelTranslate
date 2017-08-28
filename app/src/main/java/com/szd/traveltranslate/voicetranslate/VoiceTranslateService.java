package com.szd.traveltranslate.voicetranslate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nuance.speechkit.Audio;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;
import com.szd.traveltranslate.entity.LanguageInfoFactory;
import com.szd.traveltranslate.voicetranslate.translate.TranslateEntity;
import com.szd.traveltranslate.voicetranslate.translate.TranslateManager;
import com.szd.traveltranslate.voicetranslate.voice.NuanceManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VoiceTranslateService extends Service {

    private MyBinder myBinder = new MyBinder();
    public static final String TAG = "VoiceTranslateService";

    NuanceManager mNuanceManager;
    Transaction mTransaction;
    VoiceListener voiceListener;

    String languageReconginze;
    String languageSpeech;

    OnTranslateSuccess onTranslateSuccess;


    LanguageInfoFactory languageInfoFactory;

    Handler mhander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
//            showToast((String) message.obj);//翻译后的文字
            mNuanceManager.speachText(languageSpeech, (String) message.obj, voiceListener);
            return false;
        }
    });

    public VoiceTranslateService() {
    }

    public void init(Context context) {
        mNuanceManager = new NuanceManager(context);
        voiceListener = new VoiceListener();
        languageInfoFactory = new LanguageInfoFactory(context);
    }

    public void recongnizeVoce(String languageReconginze, String languageSpeach) {
        this.languageReconginze = languageReconginze;
        this.languageSpeech = languageSpeach;
        mTransaction = mNuanceManager.regcongitionVoice(languageReconginze, voiceListener);
    }

    public void stopRecording() {
        if (mTransaction != null) {
            mTransaction.stopRecording();
        }
    }


    class VoiceListener extends Transaction.Listener {
        @Override
        public void onRecognition(Transaction transaction, final Recognition recognition) {
            super.onRecognition(transaction, recognition);
//            showToast(recognition.getText());//翻译前的文字
            TranslateManager translateManager = new TranslateManager();
            String translateName = languageInfoFactory.getLanguageInfoByNuanceName(languageSpeech).getTranslateName();
            translateManager.translateString(languageReconginze, translateName, recognition.getText(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    final String result = (gson.fromJson(response.body().string(), TranslateEntity.class)).getTrans_result().get(0).getDst();
                    Message message = mhander.obtainMessage();
                    message.obj = result;
                    message.sendToTarget();

                    if (recognition.getText() != null && result != null) {
                        onTranslateSuccess.onSuccess(recognition.getText(), result);
                    }
                }
            });
        }

        @Override
        public void onStartedRecording(Transaction transaction) {
            super.onStartedRecording(transaction);
//            showToast("请开始说话");
        }

        @Override
        public void onFinishedRecording(Transaction transaction) {
            super.onFinishedRecording(transaction);
//            showToast("停止录音");
        }

        @Override
        public void onAudio(Transaction transaction, Audio audio) {
            super.onAudio(transaction, audio);
        }

        @Override
        public void onSuccess(Transaction transaction, String s) {
            super.onSuccess(transaction, s);
        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            super.onError(transaction, s, e);
            onTranslateSuccess.onFailed(s,e);
            Log.d(TAG, "onError: exception:" + e.getMessage() + "suggestion:" + s);
        }
    }


    public void setOnTranslateSuccess(OnTranslateSuccess onTranslateSuccess) {
        this.onTranslateSuccess = onTranslateSuccess;
    }


    public interface OnTranslateSuccess {
        void onSuccess(String textFrom, String textTo);
        void onFailed(String s,TransactionException e);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder {
        public VoiceTranslateService getService() {
            return VoiceTranslateService.this;
        }
    }
}
