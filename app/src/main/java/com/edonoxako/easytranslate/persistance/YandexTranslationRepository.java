package com.edonoxako.easytranslate.persistance;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.SupportedLanguage;
import com.edonoxako.easytranslate.core.TranslationRepository;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.persistance.model.TranslationEntity;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by eugene on 22.10.17.
 */

public class YandexTranslationRepository implements TranslationRepository {

    private EasyTranslateDatabase database;
    private LanguageRepository languageRepository;

    public YandexTranslationRepository(EasyTranslateDatabase database,
                                       LanguageRepository languageRepository) {
        this.database = database;
        this.languageRepository = languageRepository;
    }

    @Override
    public Flowable<List<Translation>> getAll() {
        return database.translationDao()
                .getAllTranslations()
                .map(this::toTranslations);
    }

    @Override
    public Flowable<List<Translation>> findByText(String searchText) {
        return database.translationDao()
                .getTranslationsByText(searchText)
                .map(this::toTranslations);
    }

    @Override
    public void save(Translation translation) {
        database.translationDao()
                .saveTranslation(
                        TranslationEntity.fromTranslation(translation)
                );
    }

    @Override
    public void delete(Translation translation) {
        TranslationEntity entity = TranslationEntity.fromTranslation(translation);
        database.translationDao()
                .deleteTranslations(entity);
    }

    private List<Translation> toTranslations(List<TranslationEntity> entities) {
        return Flowable.fromIterable(entities)
                .map(this::toTranslation)
                .buffer(entities.size())
                .blockingSingle();
    }

    private Translation toTranslation(TranslationEntity entity) {
        Language originLanguage = languageRepository.getLanguageByLocale(entity.getOriginLanguage())
                .blockingGet();

        Language translationLanguage = languageRepository.getLanguageByLocale(entity.getTranslationLanguage())
                .blockingGet();

        Translation translation = new Translation(
                originLanguage,
                translationLanguage,
                entity.getTextToTranslate(),
                Collections.singletonList(entity.getTranslatedText())
        );

        translation.setId(entity.getId());
        return translation;
    }
}
