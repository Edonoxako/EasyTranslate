package com.edonoxako.easytranslate;

import android.support.annotation.NonNull;

import com.edonoxako.easytranslate.core.SupportedLanguage;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.core.model.Translation;

import java.util.Collections;

import static com.edonoxako.easytranslate.core.SupportedLanguage.EN;
import static com.edonoxako.easytranslate.core.SupportedLanguage.RU;

/**
 * Created by eugene on 31.10.17.
 */

public abstract class BaseTest {

    @NonNull
    protected Translation getTranslation() {
        return getTranslation(EN, RU, "Hello", "Привет");
    }

    @NonNull
    protected Translation getTranslation(SupportedLanguage from, SupportedLanguage to, String textFrom, String translatedText) {
        Language origin = getLanguage(from);
        Language translation = getLanguage(to);

        return new Translation(
                origin,
                translation,
                textFrom,
                Collections.singletonList(translatedText)
        );
    }

    protected Language getLanguage(SupportedLanguage supportedLanguage) {
        return new Language(supportedLanguage.getLanguageCode(),
                String.valueOf(supportedLanguage.getNameRes()));
    }
}
