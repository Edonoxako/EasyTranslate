package com.edonoxako.easytranslate.yandex;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.yandex.api.YandexTranslatorApiMapper;
import com.edonoxako.easytranslate.core.SupportedLanguage;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.core.Translator;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by eugene on 15.10.17.
 */

public class YandexTranslator implements Translator {

    private YandexTranslatorApiMapper apiMapper;
    private LanguageRepository languageRepository;

    public YandexTranslator(YandexTranslatorApiMapper apiMapper,
                            LanguageRepository languageRepository) {

        this.apiMapper = apiMapper;
        this.languageRepository = languageRepository;
    }

    @Override
    public Single<Translation> translate(String text, Language translationLanguage) {
        String languageCode = translationLanguage.getLocale();
        return getTranslation(text, languageCode);
    }

    @Override
    public Single<Translation> translate(String text, Language originLanguage, Language translationLanguage) {
        String translationDirection = originLanguage.getLocale() + "-" + translationLanguage.getLocale();
        return getTranslation(text, translationDirection);
    }

    private Single<Translation> getTranslation(String text, String languageCode) {
        return apiMapper.translate(text, languageCode)
                .map(translatedTextDto -> {

                    List<String> translatedText = translatedTextDto.getTranslatedText();
                    String translationDirection = translatedTextDto.getTranslationDirection();
                    String[] split = translationDirection.split("-");

                    Language originLanguage = languageRepository.getLanguageByLocale(split[0])
                            .blockingGet();
                    Language translationLanguage = languageRepository.getLanguageByLocale(split[1])
                            .blockingGet();

                    return new Translation(
                            originLanguage,
                            translationLanguage,
                            text, translatedText);
                });
    }

}



