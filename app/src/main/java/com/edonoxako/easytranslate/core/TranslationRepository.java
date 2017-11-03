package com.edonoxako.easytranslate.core;

import com.edonoxako.easytranslate.core.model.Translation;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by eugene on 15.10.17.
 */

public interface TranslationRepository {

    Flowable<List<Translation>> getAll();

    Flowable<List<Translation>> findByText(String searchText);

    void save(Translation translation);

    void delete(Translation translation);

}
