package com.edonoxako.easytranslate.domain;

import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.support.annotation.NonNull;

import com.edonoxako.easytranslate.BaseTest;
import com.edonoxako.easytranslate.core.LanguageRepository;
import com.edonoxako.easytranslate.core.TranslationRepository;
import com.edonoxako.easytranslate.core.Translator;
import com.edonoxako.easytranslate.core.model.Translation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;

import io.reactivex.Single;

import static com.edonoxako.easytranslate.persistance.model.SupportedLanguage.*;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

/**
 * Created by eugene on 28.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TranslationInteractorTest extends BaseTest {

    @Mock private Translator translator;
    @Mock private TranslationRepository translationRepository;
    @Mock private LanguageRepository languageRepository;

    private TranslationInteractor useCase;

    @Before
    public void setUp() throws Exception {
        useCase = new TranslationInteractor(translator, translationRepository, languageRepository);
    }

    @Test
    public void testTranslateWithTranslationLanguageOnly() throws Exception {
        Translation expected = prepareTranslator();

        Single<Translation> result = useCase.translate("Hello", getLanguage(RU));

        Translation actual = result.blockingGet();
        assertThat(actual, is(expected));
    }

    @Test
    public void testTranslateWithBothLanguages() throws Exception {
        Translation expected = getTranslation();
        when(translator.translate(eq("Hello"), eq(getLanguage(EN)), eq(getLanguage(RU))))
                .thenReturn(Single.just(expected));

        Single<Translation> result = useCase.translate("Hello", getLanguage(EN), getLanguage(RU));

        Translation actual = result.blockingGet();
        assertThat(actual, is(expected));
    }

    @Test
    public void testSaveTranslation() throws Exception {
        Translation expected = prepareTranslator();

        Single<Translation> result = useCase.translate("Hello", getLanguage(RU));

        Translation actual = result.blockingGet();
        assertThat(actual, is(expected));
        Mockito.verify(translationRepository).save(eq(expected));
    }

    @Test
    public void testTranslateWhenTextToTranslateIsNull() throws Exception {
        Single<Translation> result = useCase.translate(null, getLanguage(RU));

        result.test()
                .assertError(IllegalArgumentException.class);
    }

    @Test
    public void testTranslateWhenOriginLanguageIsNull() throws Exception {
        Single<Translation> result = useCase.translate("Hello", null);

        result.test()
                .assertError(IllegalArgumentException.class);
    }

    @Test
    public void testTranslateWhenErrorOccursInNetworkCall() throws Exception {
        when(translator.translate("Hello", getLanguage(RU)))
                .thenReturn(Single.error(new UnknownHostException("translate.yandex.net")));
        doThrow(NullPointerException.class)
                .when(translationRepository).save(null);

        Single<Translation> result = useCase.translate("Hello", getLanguage(RU));

        result.test()
                .assertNoValues()
                .assertError(UnknownHostException.class);
    }

    @Test
    public void testTranslateWhenErrorOccursDuringDbOperation() throws Exception {
        prepareTranslator();
        doThrow(SQLiteDatabaseCorruptException.class)
                .when(translationRepository).save(Mockito.any(Translation.class));

        Single<Translation> result = useCase.translate("Hello", getLanguage(RU));

        result.test()
                .assertNoValues()
                .assertError(SQLiteDatabaseCorruptException.class);
    }

    @NonNull
    private Translation prepareTranslator() {
        Translation expected = getTranslation();
        when(translator.translate(eq("Hello"), eq(getLanguage(RU))))
                .thenReturn(Single.just(expected));
        return expected;
    }
}