package com.szd.traveltranslate.voicetranslate.voice;


import android.content.Context;

import com.nuance.speechkit.DetectionType;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.RecognitionType;
import com.nuance.speechkit.ResultDeliveryType;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;

/**
 * Created by shengzidong on 2017/6/29.
 */

public class NuanceManager {

    Context context;
    Session mSession;
    Transaction.Options mOptions;

    public NuanceManager(Context context) {
        this.context = context;
        mSession = Session.Factory.session(context, NuanceConfiguration.SERVER_URI, NuanceConfiguration.APP_KEY);
        mOptions = new Transaction.Options();

    }


    /**
     * 语音识别
     * @param language 识别语言
     * @param listener 监听
     * @return
     */
    public Transaction regcongitionVoice(String language, Transaction.Listener listener) {
        mOptions.setLanguage(new Language(language));
        mOptions.setRecognitionType(RecognitionType.DICTATION);
        mOptions.setDetection(DetectionType.None);
        mOptions.setResultDeliveryType(ResultDeliveryType.FINAL);//只显示最终结果
        return mSession.recognize(mOptions, listener);
    }

    /**
     * 语音合成
     * @param language 目标语言
     * @param text  合成内容
     * @param listener 监听
     * @return
     */
    public Transaction speachText(String language,String text,Transaction.Listener listener){
        mOptions.setLanguage(new Language(language));
        mOptions.setAutoplay(true);
        return  mSession.speakString(text,mOptions,listener);
    }
}
