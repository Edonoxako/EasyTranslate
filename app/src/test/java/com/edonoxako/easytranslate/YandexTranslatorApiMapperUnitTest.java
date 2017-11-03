package com.edonoxako.easytranslate;

import com.edonoxako.easytranslate.yandex.api.model.DetectedLanguageDto;
import com.edonoxako.easytranslate.yandex.api.model.LanguageListDto;
import com.edonoxako.easytranslate.yandex.api.model.TranslatedTextDto;
import com.edonoxako.easytranslate.yandex.api.YandexTranslatorApiMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by eugene on 14.10.17.
 */

public class YandexTranslatorApiMapperUnitTest {

    private static final String TRANSLATE_YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";


    private YandexTranslatorApiMapper apiMapper;

    @Before
    public void setUp() throws Exception {
        apiMapper = createApiMapper();
    }

    @Test
    public void testGetLanguageList() throws Exception {
        Map<String, String> languages = apiMapper.getLanguagesFor("en")
                .map(LanguageListDto::getLanguages)
                .blockingGet();

        assertThat(languages.get("af"), is("Afrikaans"));
    }

    @Test
    public void testDetectTextLanguage() throws Exception {
        DetectedLanguageDto detectedLanguageDto = apiMapper.detectLanguageByText("Some text")
                .blockingGet();

        assertThat(detectedLanguageDto.getCode(), is(200));
        assertThat(detectedLanguageDto.getLanguageCode(), is("en"));
    }

    @Test
    public void testTranslateTextWhenSourceLanguageUndefined() throws Exception {
        TranslatedTextDto translatedTextDto = apiMapper.translate("Hello", "ru")
                .blockingGet();

        assertThat(translatedTextDto.getCode(), is(200));
        assertThat(translatedTextDto.getTranslationDirection(), is("en-ru"));
        assertThat(translatedTextDto.getTranslatedText().get(0), is("Привет"));
    }

    private YandexTranslatorApiMapper createApiMapper() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(TRANSLATE_YANDEX_BASE_URL)
                .build();

        return retrofit.create(YandexTranslatorApiMapper.class);
    }
}
