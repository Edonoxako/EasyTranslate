package com.edonoxako.easytranslate;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.yandex.api.YandexTranslatorApiMapper;
import com.edonoxako.easytranslate.yandex.api.model.TranslatedTextDto;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.yandex.YandexTranslator;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import io.reactivex.Single;

import static com.edonoxako.easytranslate.persistance.model.SupportedLanguage.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by eugene on 15.10.17.
 */

public class YandexTranslatorTest extends BaseTest {

    private YandexTranslatorApiMapper apiMapper;
    private YandexTranslator translatorManager;

    @Before
    public void setUp() throws Exception {
        apiMapper = mock(YandexTranslatorApiMapper.class);

        LanguageRepository languageRepository = mock(LanguageRepository.class);
        when(languageRepository.getLanguageByLocale("en")).thenReturn(Single.just(getLanguage(EN)));
        when(languageRepository.getLanguageByLocale("ru")).thenReturn(Single.just(getLanguage(RU)));

        translatorManager = new YandexTranslator(apiMapper, languageRepository);
    }

    @Test
    public void testTranslateWithTranslationLanguageOnly() throws Exception {
        when(apiMapper.translate(anyString(), eq("ru")))
                .thenReturn(Single.just(createTestTranslatedText()));

        Translation translation = translatorManager
                .translate("Hello", getLanguage(RU))
                .blockingGet();

        checkTranslation(translation);
    }

    @Test
    public void testTranslateWithBothOriginAndTranslationsLanguages() throws Exception {
        when(apiMapper.translate(anyString(), eq("en-ru")))
                .thenReturn(Single.just(createTestTranslatedText()));

        Translation translation = translatorManager
                .translate("Hello", getLanguage(EN), getLanguage(RU))
                .blockingGet();

        checkTranslation(translation);
    }

    private void checkTranslation(Translation translation) {
        assertThat(translation.getTextToTranslate(), is("Hello"));
        assertThat(translation.getTranslatedText().get(0), is("Привет"));
        assertThat(translation.getOriginLanguage(), is(getLanguage(EN)));
        assertThat(translation.getTranslationLanguage(), is(getLanguage(RU)));
    }

    private TranslatedTextDto createTestTranslatedText() {
        TranslatedTextDto translatedTextDto = new TranslatedTextDto();
        translatedTextDto.setCode(200);
        translatedTextDto.setTranslationDirection("en-ru");
        translatedTextDto.setTranslatedText(Collections.singletonList("Привет"));
        return translatedTextDto;
    }
}
