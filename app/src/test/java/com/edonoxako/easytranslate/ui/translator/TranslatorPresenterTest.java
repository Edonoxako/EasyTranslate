package com.edonoxako.easytranslate.ui.translator;

import com.edonoxako.easytranslate.BaseTest;
import com.edonoxako.easytranslate.core.SchedulerFactory;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.domain.TranslationInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static com.edonoxako.easytranslate.persistance.model.SupportedLanguage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    SchedulerFactory schedulerFactory;

    private TranslatorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new TranslatorPresenter();
        presenter.interactor = useCase;
        presenter.schedulerFactory = schedulerFactory;

        when(useCase.getLanguages()).thenReturn(Single.just(Collections.emptyList()));
        when(schedulerFactory.io()).thenReturn(Schedulers.trampoline());
        when(schedulerFactory.mainThread()).thenReturn(Schedulers.trampoline());

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

    @Test
    public void testShowErrorWhenItOccurs() throws Exception {
        when(useCase.translate(anyString(), any(), any()))
                .thenReturn(Single.error(new UnknownHostException("test")));

        presenter.translate("Hello");

        verify(view).showErrorMessage(anyString());
    }
}