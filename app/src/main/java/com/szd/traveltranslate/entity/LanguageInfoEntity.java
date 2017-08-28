package com.szd.traveltranslate.entity;

/**
 * Created by shengzidong on 2017/7/31.
 */

public class LanguageInfoEntity {
    /**
     * 中文名
     */
    String name;
    /**
     * 翻译api使用的名称
     */
    String TranslateName;
    /**
     * 语音合成api使用的名称
     */
    String NuanceName;

    /**
     * 国旗图标的名称
     */
    String flag;



    public LanguageInfoEntity() {
    }

    public LanguageInfoEntity(String name, String translateName, String nuanceName, String flag) {
        this.name = name;
        TranslateName = translateName;
        NuanceName = nuanceName;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslateName() {
        return TranslateName;
    }

    public void setTranslateName(String translateName) {
        TranslateName = translateName;
    }

    public String getNuanceName() {
        return NuanceName;
    }

    public void setNuanceName(String nuanceName) {
        NuanceName = nuanceName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
