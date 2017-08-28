package com.szd.traveltranslate.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.szd.traveltranslate.utils.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by shengzidong on 2017/7/31.
 */

public class LanguageInfoFactory {
    private static final String TAG = "LanguageInfoFactory";
    Context context;
    LanguageJsonEntity entity;

    public LanguageInfoFactory(Context context) {
        this.context = context;
        String json = getJsonFromAssets();
        Gson gson = new Gson();
        entity = gson.fromJson(json, LanguageJsonEntity.class);
    }

    /**
     * 通过位置获得LanguageInfoEntity
     *
     * @param position 位置
     * @return
     */
    public LanguageInfoEntity getLanguageInfoByPosition(int position) {
        LanguageInfoEntity languageInfoEntity = new LanguageInfoEntity();
        languageInfoEntity.setName(entity.getLanguage().get(position).getName());
        languageInfoEntity.setTranslateName(entity.getLanguage().get(position).getTranslate());
        languageInfoEntity.setNuanceName(entity.getLanguage().get(position).getNuance());
        languageInfoEntity.setFlag(entity.getLanguage().get(position).getFlag());
        return languageInfoEntity;
    }

    /**
     * 通过中文名称获得 LanguageInfoEntity
     *
     * @param name 中文名
     * @return
     */
    public LanguageInfoEntity getLanguageInfoByName(String name) {
        LanguageInfoEntity languageInfoEntity = new LanguageInfoEntity();
        int index = 0;
        for (int i = 0; i < entity.getLanguage().size(); i++) {
            String name_temp = entity.getLanguage().get(i).getName();
            if (name.equals(name_temp)) {
                index = i;
            }
        }
        languageInfoEntity.setName(entity.getLanguage().get(index).getName());
        languageInfoEntity.setTranslateName(entity.getLanguage().get(index).getTranslate());
        languageInfoEntity.setNuanceName(entity.getLanguage().get(index).getNuance());
        languageInfoEntity.setFlag(entity.getLanguage().get(index).getFlag());
        return languageInfoEntity;
    }


    /**
     * 通过Nuance中的语言名称获得 LanguageInfoEntity
     *
     * @param name
     * @return
     */
    public LanguageInfoEntity getLanguageInfoByNuanceName(String name) {
        LanguageInfoEntity languageInfoEntity = new LanguageInfoEntity();
        int index = 0;
        for (int i = 0; i < entity.getLanguage().size(); i++) {
            String name_temp = entity.getLanguage().get(i).getNuance();
            if (name.equals(name_temp)) {
                index = i;
            }
        }
        languageInfoEntity.setName(entity.getLanguage().get(index).getName());
        languageInfoEntity.setTranslateName(entity.getLanguage().get(index).getTranslate());
        languageInfoEntity.setNuanceName(entity.getLanguage().get(index).getNuance());
        languageInfoEntity.setFlag(entity.getLanguage().get(index).getFlag());
        return languageInfoEntity;
    }

    public String[] getNameArray() {
        ArrayList<String> languages = new ArrayList<>();
        String[] language = new String[Constant.LANUAGE_COUNT];
        if (entity != null) {
            for (int i = 0; i < entity.getLanguage().size(); i++) {
                languages.add(entity.getLanguage().get(i).getName());
            }
        }
        return languages.toArray(language);
    }

    public String getJsonFromAssets() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("language.json"));
            BufferedReader bufReader = new BufferedReader(inputStreamReader);
            String line;
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
