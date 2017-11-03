package com.edonoxako.easytranslate.yandex.api;

import com.edonoxako.easytranslate.yandex.api.model.DetectedLanguageDto;
import com.edonoxako.easytranslate.yandex.api.model.LanguageListDto;
import com.edonoxako.easytranslate.yandex.api.model.TranslatedTextDto;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by eugene on 14.10.17.
 */

public interface YandexTranslatorApiMapper {

    String API_KEY = "trnsl.1.1.20171014T174757Z.5c8fe0e25142a6da.e73201cdefd5490d975646667a21bf65fb7c6249";
    String TRANSLATE_YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    @POST("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=" + API_KEY)
    Single<LanguageListDto> getLanguagesFor(
            @Query("ui") String languageCode
    );

    @POST("https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + API_KEY)
    Single<DetectedLanguageDto> detectLanguageByText(
            @Query("text") String text
    );

    @POST("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + API_KEY)
    Single<TranslatedTextDto> translate(
            @Query("text") String textToTranslate,
            @Query("lang") String translationLanguageCode
    );
}
