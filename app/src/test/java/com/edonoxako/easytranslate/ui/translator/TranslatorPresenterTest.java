package com.edonoxako.easytranslate.ui.translator;

import com.edonoxako.easytranslate.BaseTest;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.domain.TranslationInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static com.edonoxako.easytranslate.core.SupportedLanguage.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by eugene on 31.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TranslatorPresenterTest extends BaseTest {

    @Mock
    TranslatorView view;

    @Mock
    TranslationInteractor useCase;

    private TranslatorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new TranslatorPresenter();
        presenter.useCase = useCase;
        presenter.mainThreadScheduler = Schedulers.trampoline();

        when(useCase.getLanguages()).thenReturn(Single.just(Collections.emptyList()));
        presenter.attachView(view);
    }

    @Test
    public void translate() throws Exception {
        Translation translation = getTranslation();

        when(useCase.translate("Hello", getLanguage(EN), getLanguage(RU)))
                .thenReturn(Single.just(translation));

        presenter.setOriginLanguage(getLanguage(EN));
        presenter.setTranslationLanguage(getLanguage(RU));
        presenter.translate("Hello");

        verify(useCase).translate("Hello", getLanguage(EN), getLanguage(RU));
        verify(view).setTranslatedText("Привет");
    }

}