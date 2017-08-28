package com.szd.traveltranslate.module;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nuance.speechkit.TransactionException;
import com.szd.traveltranslate.R;
import com.szd.traveltranslate.adapter.TranslateAdapter;
import com.szd.traveltranslate.entity.LanguageInfoEntity;
import com.szd.traveltranslate.entity.LanguageInfoFactory;
import com.szd.traveltranslate.entity.TranslateTextEntity;
import com.szd.traveltranslate.utils.Constant;
import com.szd.traveltranslate.utils.SharePreferenceUtils;
import com.szd.traveltranslate.voicetranslate.VoiceTranslateService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAvtivity implements View.OnTouchListener
        , View.OnClickListener, View.OnLongClickListener {
    TextView tv;
    ImageButton ibRecordLeft;
    ImageButton ibRecordRight;
    ImageButton ibFlagLeft;
    ImageButton ibFlagRight;
    RecyclerView rvRecord;
    TranslateAdapter translateAdapter;

    //语音翻译的后台服务
    VoiceTranslateService voiceTranslateService;

    String languageReconginze;
    String languageSpeach;
    String languageLeft = "cmn-CHN";
    String languageRight = "eng-USA";
    boolean isVoiceServiceConnected = false;

    String[] languages;
    LanguageInfoFactory languageInfoFactory;

    //保存翻译过的所有文字、语言类别信息
    List<TranslateTextEntity> translateTexts;

    public static final int FLAG_SELECT_LEFT = 100;
    public static final int FLAG_SELECT_RIGHT = 101;
    public static final String LANGUAGE_SELECTED = "language_selected";

    SharePreferenceUtils sharePreferenceUtils;


    public static final String TAG = "MainActivity";


    @Override
    public void init() {
        languageInfoFactory = new LanguageInfoFactory(this);
        languages = languageInfoFactory.getNameArray();

        translateTexts = new ArrayList<>();
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        translateAdapter = new TranslateAdapter(this, translateTexts);
        rvRecord.setAdapter(translateAdapter);

        sharePreferenceUtils = new SharePreferenceUtils(this);

        //启动App时取出推出时保存的语言种类
        languageLeft=sharePreferenceUtils.getValueFromKey(Constant.LANGUAGE_LEFT);
        languageRight=sharePreferenceUtils.getValueFromKey(Constant.LANGUAGE_RIGHT);


        ibFlagLeft.setImageResource(getResources().getIdentifier(languageInfoFactory.getLanguageInfoByNuanceName(languageLeft).getFlag(),"drawable",getPackageName()));
        ibFlagRight.setImageResource(getResources().getIdentifier(languageInfoFactory.getLanguageInfoByNuanceName(languageRight).getFlag(),"drawable",getPackageName()));
    }


    private void getVoice(String languageFrom, String languageTo) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //如未获得录音权限，则提示用户获取
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        } else {
            if (!isVoiceServiceConnected) {
                isVoiceServiceConnected = bindService(new Intent(this, VoiceTranslateService.class), serviceConnection, Context.BIND_AUTO_CREATE);
            }
            if (isVoiceServiceConnected && voiceTranslateService != null) {
                voiceTranslateService.recongnizeVoce(languageFrom, languageTo);
            }
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            VoiceTranslateService.MyBinder myBinder = (VoiceTranslateService.MyBinder) iBinder;
            voiceTranslateService = myBinder.getService();
            voiceTranslateService.init(MainActivity.this);
            voiceTranslateService.recongnizeVoce(languageReconginze, languageSpeach);


            voiceTranslateService.setOnTranslateSuccess(new VoiceTranslateService.OnTranslateSuccess() {
                @Override
                public void onSuccess(String textFrom, String textTo) {
                    //回调处理 拿到识别的文字显示到list中
                    LanguageInfoEntity languageInfoFrom = languageInfoFactory.getLanguageInfoByNuanceName(languageReconginze);
                    LanguageInfoEntity languageInfoTo = languageInfoFactory.getLanguageInfoByNuanceName(languageSpeach);
                    TranslateTextEntity translateTextEntity = new TranslateTextEntity(textFrom, textTo, languageInfoFrom, languageInfoTo);
                    showResultOnList(translateTextEntity);

                    dismissDialog();

                }

                @Override
                public void onFailed(String s, TransactionException e) {
                    dismissDialog();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            voiceTranslateService.stopRecording();
            isVoiceServiceConnected = false;
        }
    };

    //讲翻译结果显示到List中
    public void showResultOnList(final TranslateTextEntity translateTextEntity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                translateTexts.add(translateTextEntity);
                translateAdapter.notifyDataSetChanged();
                rvRecord.smoothScrollToPosition(translateAdapter.getItemCount() - 1);
            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent languageSelectIntent = new Intent(this, LanguageSelectActivity.class);
        languageSelectIntent.putExtra("languages", languages);
        switch (view.getId()) {
            case R.id.ib_flag_left:
                startActivityForResult(languageSelectIntent, FLAG_SELECT_LEFT);
                break;
            case R.id.ib_flag_right:
                startActivityForResult(languageSelectIntent, FLAG_SELECT_RIGHT);
                break;
        }
    }

    //监听录音按钮的按下和弹起
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //首先获得点击的按钮来判断需要识别和翻译分别为哪个语种
        switch (view.getId()) {
            case R.id.ib_voice_left:
                languageReconginze = languageLeft;
                languageSpeach = languageRight;
                break;
            case R.id.ib_voice_right:
                languageReconginze = languageRight;
                languageSpeach = languageLeft;
                break;
        }
        //获得需要识别的语种后开始识别，抬起手指停止识别
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getVoice(languageReconginze, languageSpeach);
                showMyDialog(R.string.dialog_start, true);
                break;
            case MotionEvent.ACTION_UP:
                if (voiceTranslateService != null) {
                    voiceTranslateService.stopRecording();
                }
                showMyDialog(R.string.processing, true);
                break;
        }
        return false;
    }


    //TODO 暂时不用 初步想法改成国旗图标单击更改语言，长按开始说话。
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.ib_flag_left:
                Log.d(TAG, "onLongClick: left");
                break;
            case R.id.ib_flag_right:
                Log.d(TAG, "onLongClick: right");
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 0) {
            LanguageInfoEntity entity = languageInfoFactory.getLanguageInfoByName(data.getStringExtra(LANGUAGE_SELECTED));
            int flag = getResources().getIdentifier(entity.getFlag(), "drawable", getPackageName());
            switch (requestCode) {
                case FLAG_SELECT_LEFT:
                    Log.d(TAG, "onActivityResult: left");
                    languageLeft = entity.getNuanceName();
                    ibFlagLeft.setImageResource(flag);
                    sharePreferenceUtils.add(Constant.LANGUAGE_LEFT, languageLeft);
                    break;
                case FLAG_SELECT_RIGHT:
                    Log.d(TAG, "onActivityResult: right");
                    languageRight = entity.getNuanceName();
                    ibFlagRight.setImageResource(flag);
                    sharePreferenceUtils.add(Constant.LANGUAGE_RIGHT, languageRight);
                    break;
            }
        }
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv = (TextView) findViewById(R.id.tvTitle);
        tv.setText(R.string.title_translate);
        ibRecordLeft = (ImageButton) findViewById(R.id.ib_voice_left);
        ibRecordLeft.setOnTouchListener(this);
        ibRecordRight = (ImageButton) findViewById(R.id.ib_voice_right);
        ibRecordRight.setOnTouchListener(this);
        ibFlagLeft = (ImageButton) findViewById(R.id.ib_flag_left);
        ibFlagRight = (ImageButton) findViewById(R.id.ib_flag_right);
        ibFlagLeft.setOnClickListener(this);
        ibFlagRight.setOnClickListener(this);
        ibFlagLeft.setOnLongClickListener(this);
        ibFlagRight.setOnLongClickListener(this);

        rvRecord = (RecyclerView) findViewById(R.id.rvRecord);


    }


    @Override
    public int getResLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isVoiceServiceConnected) {
            unbindService(serviceConnection);
        }
    }
}
