package com.edonoxako.easytranslate.ui.translator;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.edonoxako.easytranslate.core.model.Language;
import com.edonoxako.easytranslate.domain.TranslationInteractor;

import javax.inject.Inject;

import io.reactivex.Scheduler;

/**
 * Created by eugene on 15.10.17.
 */
@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView>{

    @Inject
    public TranslationInteractor useCase;
    @Inject
    public Scheduler mainThreadScheduler;

    private Language originLanguage;
    private Language translationLanguage;

    public void setOriginLanguage(Language originLanguage) {
        this.originLanguage = originLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public void translate(String textToTranslate) {
        useCase.translate(textToTranslate, originLanguage, translationLanguage)
                .observeOn(mainThreadScheduler)
                .subscribe(translation ->
                        getViewState().setTranslatedText(translation.getTranslatedText().get(0))
                );
    }

    @Override
    protected void onFirstViewAttach() {
       useCase.getLanguages()
               .observeOn(mainThreadScheduler)
               .subscribe(languages -> getViewState().setSupportedLanguages(languages));
    }
}
