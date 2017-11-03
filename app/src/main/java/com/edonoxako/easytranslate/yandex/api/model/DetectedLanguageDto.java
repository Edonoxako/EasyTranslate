package com.edonoxako.easytranslate.yandex.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eugene on 15.10.17.
 */

public class DetectedLanguageDto extends YandexTranslateStatusedResponse {

    @SerializedName("lang")
    private String languageCode;

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
