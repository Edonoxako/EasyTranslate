package com.edonoxako.easytranslate.persistance;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.edonoxako.easytranslate.persistance.model.TranslationEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by eugene on 19.10.17.
 */

@Dao
public interface TranslationDao {

    @Query("SELECT * FROM Translation")
    Flowable<List<TranslationEntity>> getAllTranslations();

    @Query("SELECT * FROM Translation WHERE textToTranslate LIKE :text OR translatedText LIKE :text")
    Flowable<List<TranslationEntity>> getTranslationsByText(String text);

    @Insert
    void saveTranslation(TranslationEntity translation);

    @Delete
    void deleteTranslations(TranslationEntity... entity);

}
