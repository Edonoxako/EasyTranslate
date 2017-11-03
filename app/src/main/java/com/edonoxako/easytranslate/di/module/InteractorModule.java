package com.edonoxako.easytranslate.di.module;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.TranslationRepository;
import com.edonoxako.easytranslate.core.Translator;
import com.edonoxako.easytranslate.domain.TranslationInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eugene on 02.11.17.
 */
@Module
public class InteractorModule {

    @Provides
    public TranslationInteractor translationUseCase(Translator translator,
                                                    TranslationRepository translationRepository,
                                                    LanguageRepository languageRepository) {

        return new TranslationInteractor(translator, translationRepository, languageRepository);
    }

}
