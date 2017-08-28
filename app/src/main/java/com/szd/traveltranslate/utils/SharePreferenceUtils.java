package com.szd.traveltranslate.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shengzidong on 2017/8/7.
 */

public class SharePreferenceUtils {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;



    public  SharePreferenceUtils(Context context) {
        this.context=context;
        sharedPreferences = context.getSharedPreferences(Constant.SHAREPREFERENCE_TABLE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public boolean add(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String getValueFromKey(String key) {
        if (Constant.LANGUAGE_LEFT.equals(key)) {
            return sharedPreferences.getString(key, "cmn-CHN");
        } else {
            return sharedPreferences.getString(key, "eng-USA");
        }
    }
}
