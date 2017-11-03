package com.edonoxako.easytranslate.core;

import com.edonoxako.easytranslate.core.model.Language;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by eugene on 03.11.17.
 */

public interface LanguageRepository {

    Single<List<Language>> getAllLanguages();

    Single<Language> getLanguageByLocale(String locale);

}
