package com.edonoxako.easytranslate.ui.translator;

import com.arellomobile.mvp.MvpView;
import com.edonoxako.easytranslate.core.model.Language;

import java.util.List;

/**
 * Created by eugene on 15.10.17.
 */

public interface TranslatorView extends MvpView {

    void setSupportedLanguages(List<Language> languages);

    void setOriginLanguage(Language language);

    void setTranslationLanguage(Language language);

    void setTranslatedText(String translatedText);

}
