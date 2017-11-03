package com.edonoxako.easytranslate.yandex.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eugene on 15.10.17.
 */

public class TranslatedTextDto extends YandexTranslateStatusedResponse {

    @SerializedName("lang")
    private String translationDirection;

    @SerializedName("text")
    private List<String> translatedText;


    public String getTranslationDirection() {
        return translationDirection;
    }

    public void setTranslationDirection(String translationDirection) {
        this.translationDirection = translationDirection;
    }

    public List<String> getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(List translatedText) {
        this.translatedText = translatedText;
    }
}
