package com.edonoxako.easytranslate.persistance.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.edonoxako.easytranslate.core.model.Translation;

/**
 * Created by eugene on 19.10.17.
 */

@Entity(tableName = "translation")
public class TranslationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String originLanguage;
    private String translationLanguage;
    private String textToTranslate;
    private String translatedText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginLanguage() {
        return originLanguage;
    }

    public void setOriginLanguage(String originLanguage) {
        this.originLanguage = originLanguage;
    }

    public String getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public void setTextToTranslate(String textToTranslate) {
        this.textToTranslate = textToTranslate;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranslationEntity that = (TranslationEntity) o;

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

    public static TranslationEntity fromTranslation(Translation translation) {
        TranslationEntity entity = new TranslationEntity();
        entity.setOriginLanguage(translation.getOriginLanguage().getLocale());
        entity.setTranslationLanguage(translation.getTranslationLanguage().getLocale());
        entity.setTextToTranslate(translation.getTextToTranslate());
        entity.setTranslatedText(translation.getTranslatedText().get(0));
        entity.setId(translation.getId());
        return entity;
    }
}
