package com.edonoxako.easytranslate.domain;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.SchedulerFactory;
import com.edonoxako.easytranslate.core.TranslationRepository;
import com.edonoxako.easytranslate.core.Translator;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.core.model.Translation;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by eugene on 28.10.17.
 */

public class TranslationInteractor {


    private Translator translator;
    private TranslationRepository translationRepository;
    private LanguageRepository languageRepository;

    public TranslationInteractor(Translator translator,
                                 TranslationRepository translationRepository,
                                 LanguageRepository languageRepository) {

        this.translator = translator;
        this.translationRepository = translationRepository;
        this.languageRepository = languageRepository;
    }

    public Single<Translation> translate(String textToTranslate, Language translationLanguage) {
        if (textToTranslate == null) return Single.error(new IllegalArgumentException(
                "textToTranslate in null but can't be!"
        ));
        if (translationLanguage == null) return Single.error(new IllegalArgumentException(
                "translationLanguage is null but can't be!"
        ));

        return translator.translate(textToTranslate, translationLanguage)
                .doOnEvent(this::saveToRepository);
    }

    public Single<Translation> translate(String textToTranslate, Language originLanguage,
                                         Language translationLanguage) {
        if (textToTranslate == null) return Single.error(new IllegalArgumentException(
                "textToTranslate in null but can't be!"
        ));
        if (originLanguage == null) return Single.error(new IllegalArgumentException(
                "originLanguage is null but can't be!"
        ));
        if (translationLanguage == null) return Single.error(new IllegalArgumentException(
                "translationLanguage is null but can't be!"
        ));

        return translator.translate(textToTranslate, originLanguage, translationLanguage)
                .doOnEvent(this::saveToRepository);
    }

    public Single<List<Language>> getLanguages() {
        return languageRepository.getAllLanguages();
    }

    private void saveToRepository(Translation translation, Throwable throwable) {
        if (throwable == null && translation != null)
            translationRepository.save(translation);
    }
}
