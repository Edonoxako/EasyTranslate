package com.edonoxako.easytranslate.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.edonoxako.easytranslate.core.DefaultSchedulerFactory;
import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.SchedulerFactory;
import com.edonoxako.easytranslate.core.TranslationRepository;
import com.edonoxako.easytranslate.core.Translator;
import com.edonoxako.easytranslate.persistance.EasyTranslateDatabase;
import com.edonoxako.easytranslate.persistance.FixedLanguageRepository;
import com.edonoxako.easytranslate.persistance.YandexTranslationRepository;
import com.edonoxako.easytranslate.yandex.YandexTranslator;
import com.edonoxako.easytranslate.yandex.api.YandexTranslatorApiMapper;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eugene on 02.11.17.
 */
@Module
public class ExternalApiModule {

    private Context appContext;

    public ExternalApiModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    public TranslationRepository translationRepository(EasyTranslateDatabase database,
                                                       LanguageRepository languageRepository) {
        return new YandexTranslationRepository(database, languageRepository);
    }

    @Provides
    public EasyTranslateDatabase easyTranslateDatabase(Context context) {
        return Room.databaseBuilder(context, EasyTranslateDatabase.class, "Translations")
                .build();
    }

    @Provides
    public Translator translator(YandexTranslatorApiMapper apiMapper,
                                 LanguageRepository languageRepository) {
        return new YandexTranslator(apiMapper, languageRepository);
    }

    @Provides
    public YandexTranslatorApiMapper apiMapper() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(YandexTranslatorApiMapper.TRANSLATE_YANDEX_BASE_URL)
                .build();

        return retrofit.create(YandexTranslatorApiMapper.class);
    }

    @Provides
    public LanguageRepository languageRepository(Context context) {
        return new FixedLanguageRepository(context);
    }

    @Provides
    public Context provideContext() {
        return appContext;
    }
}
