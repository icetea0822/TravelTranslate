package com.szd.traveltranslate.entity;

/**
 * Created by shengzidong on 2017/8/2.
 */

public class TranslateTextEntity {
    /**
     * 翻译前的文字
     */
    String textFrom;

    /**
     * 翻译后的文字
     */
    String textTo;

    /**
     * 翻译前文字的语言信息
     */
    LanguageInfoEntity languageInfoFrom;

    /**
     * 翻译后文字的语言信息
     */
    LanguageInfoEntity languageInfoTo;

    public TranslateTextEntity(String textFrom, String textTo, LanguageInfoEntity languageInfoFrom, LanguageInfoEntity languageInfoTo) {
        this.textFrom = textFrom;
        this.textTo = textTo;
        this.languageInfoFrom = languageInfoFrom;
        this.languageInfoTo = languageInfoTo;
    }

    public String getTextFrom() {
        return textFrom;
    }

    public void setTextFrom(String textFrom) {
        this.textFrom = textFrom;
    }

    public String getTextTo() {
        return textTo;
    }

    public void setTextTo(String textTo) {
        this.textTo = textTo;
    }

    public LanguageInfoEntity getLanguageInfoFrom() {
        return languageInfoFrom;
    }

    public void setLanguageInfoFrom(LanguageInfoEntity languageInfoFrom) {
        this.languageInfoFrom = languageInfoFrom;
    }

    public LanguageInfoEntity getLanguageInfoTo() {
        return languageInfoTo;
    }

    public void setLanguageInfoTo(LanguageInfoEntity languageInfoTo) {
        this.languageInfoTo = languageInfoTo;
    }
}
