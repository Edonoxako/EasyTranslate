package com.edonoxako.easytranslate.core;

import com.edonoxako.easytranslate.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eugene on 15.10.17.
 */

public enum SupportedLanguage {

    RU("ru", R.string.supported_lang_ru),
    EN("en", R.string.supported_lang_en),
    FR("fr", R.string.supported_lang_fr),
    DE("de", R.string.supported_lang_de),
    SP("sp", R.string.supported_lang_sp);

    private String languageCode;
    private int nameRes;

    SupportedLanguage(String languageCode, int nameRes) {
        this.languageCode = languageCode;
        this.nameRes = nameRes;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public int getNameRes() {
        return nameRes;
    }

    public static SupportedLanguage fromLanguageCode(String languageCode) {
        for (SupportedLanguage value : SupportedLanguage.values()) {
            if (value.getLanguageCode().equals(languageCode)) {
                return value;
            }
        }
        throw new IllegalArgumentException(languageCode + " language is not supported");
    }

    public static List<SupportedLanguage> getAll() {
        return Arrays.asList(values());
    }
}
