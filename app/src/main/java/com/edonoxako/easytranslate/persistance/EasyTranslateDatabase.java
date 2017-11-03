package com.edonoxako.easytranslate.persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.edonoxako.easytranslate.persistance.model.TranslationEntity;

/**
 * Created by eugene on 19.10.17.
 */

@Database(entities = {TranslationEntity.class}, version = 1)
public abstract class EasyTranslateDatabase extends RoomDatabase {
    public abstract TranslationDao translationDao();
}
