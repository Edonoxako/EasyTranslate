package com.edonoxako.easytranslate.persistance;

import android.content.Context;
import android.support.annotation.NonNull;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.SupportedLanguage;
import com.edonoxako.easytranslate.core.model.Language;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by eugene on 03.11.17.
 */

public class FixedLanguageRepository implements LanguageRepository {

    private Context context;

    public FixedLanguageRepository(Context context) {
        this.context = context;
    }

    @Override
    public Single<List<Language>> getAllLanguages() {
        return Flowable.fromIterable(SupportedLanguage.getAll())
                .map(this::toLanguage)
                .toList();
    }

    @Override
    public Single<Language> getLanguageByLocale(String locale) {
        return Single.just(SupportedLanguage.fromLanguageCode(locale))
                .map(this::toLanguage);
    }

    @NonNull
    private Language toLanguage(SupportedLanguage supportedLanguage) {
        return new Language(supportedLanguage.getLanguageCode(),
                context.getString(supportedLanguage.getNameRes()));
    }
}
