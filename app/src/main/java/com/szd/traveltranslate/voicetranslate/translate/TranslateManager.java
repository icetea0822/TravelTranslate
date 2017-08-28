package com.szd.traveltranslate.voicetranslate.translate;

import com.szd.traveltranslate.utils.MD5;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by shengzidong on 2017/7/2.
 */

public class TranslateManager {
    OkHttpClient mClient;
    String url="http://api.fanyi.baidu.com/api/trans/vip/translate";
    String appid="20170702000061751";
    String key="9l1Es6Q3GslzGrkF4tXQ";
    String from;
    String to;
    String q;//要查询的文字
    String salt;//一个随机数
    String sign;

    public TranslateManager() {
        mClient=new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
    }

    public void translateString(String languageFrom, String languageTo, String text, Callback callback){
        from=languageFrom;
        to=languageTo;
        q=text;
        salt=String.valueOf(System.currentTimeMillis());
        sign= MD5.md5(appid+q+salt+key);

        FormBody formBody=new FormBody.Builder()
                .add("q",q)
                .add("from",from)
                .add("to",to)
                .add("appid",appid)
                .add("salt",salt)
                .add("sign",sign)
                .build();

        Request request=new Request.Builder().url(url).post(formBody).build();
        Call call=mClient.newCall(request);
        call.enqueue(callback);
    }
}
