package com.edonoxako.easytranslate;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;

import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.persistance.EasyTranslateDatabase;
import com.edonoxako.easytranslate.persistance.YandexTranslationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static com.edonoxako.easytranslate.persistance.model.SupportedLanguage.EN;
import static com.edonoxako.easytranslate.persistance.model.SupportedLanguage.RU;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by eugene on 19.10.17.
 */

@RunWith(RobolectricTestRunner.class)
public class YandexTranslationRepositoryTest extends BaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private YandexTranslationRepository repository;
    private EasyTranslateDatabase db;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application,
                EasyTranslateDatabase.class)
                .allowMainThreadQueries()
                .build();

        LanguageRepository languageRepository = mock(LanguageRepository.class);
        when(languageRepository.getLanguageByLocale("en")).thenReturn(Single.just(getLanguage(EN)));
        when(languageRepository.getLanguageByLocale("ru")).thenReturn(Single.just(getLanguage(RU)));

        repository = new YandexTranslationRepository(db, languageRepository);
    }

    @Test
    public void testSaveTranslation() throws Exception {
        Translation translation = getTranslation(EN, RU, "Hello", "Привет");

        repository.save(translation);

        db.translationDao()
                .getAllTranslations()
                .map(translationEntities -> translationEntities.get(0))
                .test()
                .assertValue(translationEntity -> translationEntity.getOriginLanguage().equals(translation.getOriginLanguage().getLocale()))
                .assertValue(translationEntity -> translationEntity.getTranslationLanguage().equals(translation.getTranslationLanguage().getLocale()))
                .assertValue(translationEntity -> translationEntity.getTextToTranslate().equals(translation.getTextToTranslate()))
                .assertValue(translationEntity -> translationEntity.getTranslatedText().equals(translation.getTranslatedText().get(0)));
    }

    @Test
    public void testGetAllTranslations() throws Exception {
        Translation translation1 = getTranslation(EN, RU, "Hello", "Привет");
        Translation translation2 = getTranslation(EN, RU, "Bye", "Пока");
        Translation translation3 = getTranslation(EN, RU, "Go fuck yourself", "Приятно познакомиться");

        repository.save(translation1);
        repository.save(translation2);
        repository.save(translation3);

        repository.getAll()
                .test()
                .assertValue(translations -> translation1.equals(translations.get(0)))
                .assertValue(translations -> translation2.equals(translations.get(1)))
                .assertValue(translations -> translation3.equals(translations.get(2)))
                .assertNotComplete();
    }

    @Test
    public void testGetAllTranslationsWhenDatabaseIsEmpty() throws Exception {
        repository.getAll()
                .test()
                .assertNoValues()
                .assertNotComplete();
    }

    @Test
    public void testFindByOriginText() throws Exception {
        Translation translation1 = getTranslation(EN, RU, "Hello", "Привет");
        Translation translation2 = getTranslation(EN, RU, "Bye", "Пока");

        repository.save(translation1);
        repository.save(translation2);

        repository.findByText("Bye")
                .flatMap(Flowable::fromIterable)
                .test()
                .assertValue(translation2);
    }

    @Test
    public void testFindByTranslatedText() throws Exception {
        Translation translation1 = getTranslation(EN, RU, "Hello", "Привет");
        Translation translation2 = getTranslation(EN, RU, "Bye", "Пока");

        repository.save(translation1);
        repository.save(translation2);

        repository.findByText("Привет")
                .flatMap(Flowable::fromIterable)
                .test()
                .assertValue(translation1);
    }

    @Test
    public void testFindByOriginTextWhenNoValuesFound() throws Exception {
        Translation translation1 = getTranslation(EN, RU, "Hello", "Привет");
        Translation translation2 = getTranslation(EN, RU, "Bye", "Пока");

        repository.save(translation1);
        repository.save(translation2);

        repository.findByText("Lol")
                .test()
                .assertNoValues()
                .assertNotComplete();
    }

    @Test
    public void testDelete() throws Exception {
        Translation translation = getTranslation();
        repository.save(translation);
        Translation savedTranslation = repository.getAll()
                .blockingFirst()
                .get(0);

        repository.delete(savedTranslation);

        db.translationDao()
                .getAllTranslations()
                .flatMap(Flowable::fromIterable)
                .test()
                .assertEmpty();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }
}
