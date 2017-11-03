package com.edonoxako.easytranslate.yandex.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by eugene on 15.10.17.
 */

public class LanguageListDto {

    @Expose
    @SerializedName("langs")
    private Map<String, String> languages;

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

}
