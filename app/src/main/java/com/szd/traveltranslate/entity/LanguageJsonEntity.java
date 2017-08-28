package com.szd.traveltranslate.entity;

import java.util.List;

/**
 * Created by shengzidong on 2017/7/31.
 */

public class LanguageJsonEntity {


    private List<LanguageBean> language;

    public List<LanguageBean> getLanguage() {
        return language;
    }

    public void setLanguage(List<LanguageBean> language) {
        this.language = language;
    }

    public static class LanguageBean {
        /**
         * name : 中文
         * translate : zh
         * nuance : cmn-CHN
         * flag : flag_zh
         */

        private String name;
        private String translate;
        private String nuance;
        private String flag;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTranslate() {
            return translate;
        }

        public void setTranslate(String translate) {
            this.translate = translate;
        }

        public String getNuance() {
            return nuance;
        }

        public void setNuance(String nuance) {
            this.nuance = nuance;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
