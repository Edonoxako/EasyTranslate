package com.edonoxako.easytranslate.ui.translator;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.edonoxako.easytranslate.core.SchedulerFactory;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.core.model.Translation;
import com.edonoxako.easytranslate.domain.TranslationInteractor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by eugene on 15.10.17.
 */
@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView>{

    @Inject
    public TranslationInteractor interactor;
    @Inject
    public SchedulerFactory schedulerFactory;

    private Language originLanguage;
    private Language translationLanguage;

    public void setOriginLanguage(Language originLanguage) {
        this.originLanguage = originLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public void translate(String textToTranslate) {
        interactor.translate(textToTranslate, originLanguage, translationLanguage)
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.mainThread())
                .subscribe(this::showTranslation, this::showError);
    }

    @Override
    protected void onFirstViewAttach() {
       interactor.getLanguages()
               .subscribeOn(schedulerFactory.io())
               .observeOn(schedulerFactory.mainThread())
               .subscribe(this::setLanguages);
    }

    private void setLanguages(List<Language> languages) {
        getViewState().setSupportedLanguages(languages);
    }

    private void showTranslation(Translation translation) {
        getViewState().setTranslatedText(translation.getTranslatedText().get(0));
    }

    private void showError(Throwable throwable) {
        getViewState().showErrorMessage(throwable.getMessage());
    }
}
