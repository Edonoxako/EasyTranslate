package com.edonoxako.easytranslate.core;

import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.core.model.Translation;

import io.reactivex.Single;

/**
 * Created by eugene on 15.10.17.
 */

public interface Translator {
    Single<Translation> translate(String text, Language translationLanguage);

    Single<Translation> translate(String text, Language originLanguage, Language translationLanguage);
}
