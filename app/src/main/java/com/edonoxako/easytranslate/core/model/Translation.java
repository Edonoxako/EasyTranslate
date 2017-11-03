package com.edonoxako.easytranslate.core.model;

import android.arch.persistence.room.Entity;

import java.util.List;

/**
 * Created by eugene on 15.10.17.
 */

@Entity
public class Translation {

    private int id = 0;

    private Language originLanguage;
    private Language translationLanguage;
    private String textToTranslate;
    private List<String> translatedText;

    public Translation(Language originLanguage, Language translationLanguage, String textToTranslate, List<String> translatedText) {
        this.originLanguage = originLanguage;
        this.translationLanguage = translationLanguage;
        this.textToTranslate = textToTranslate;
        this.translatedText = translatedText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Language getOriginLanguage() {
        return originLanguage;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public List<String> getTranslatedText() {
        return translatedText;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translation that = (Translation) o;

        if (getOriginLanguage() != null ? !getOriginLanguage().equals(that.getOriginLanguage()) : that.getOriginLanguage() != null)
            return false;
        if (getTranslationLanguage() != null ? !getTranslationLanguage().equals(that.getTranslationLanguage()) : that.getTranslationLanguage() != null)
            return false;
        if (getTextToTranslate() != null ? !getTextToTranslate().equals(that.getTextToTranslate()) : that.getTextToTranslate() != null)
            return false;
        return getTranslatedText() != null ? getTranslatedText().equals(that.getTranslatedText()) : that.getTranslatedText() == null;
    }

    @Override
    public int hashCode() {
        int result = getOriginLanguage() != null ? getOriginLanguage().hashCode() : 0;
        result = 31 * result + (getTranslationLanguage() != null ? getTranslationLanguage().hashCode() : 0);
        result = 31 * result + (getTextToTranslate() != null ? getTextToTranslate().hashCode() : 0);
        result = 31 * result + (getTranslatedText() != null ? getTranslatedText().hashCode() : 0);
        return result;
    }
}
