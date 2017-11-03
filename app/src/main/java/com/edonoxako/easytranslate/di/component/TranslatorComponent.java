package com.edonoxako.easytranslate.di.component;

import com.edonoxako.easytranslate.di.module.ExternalApiModule;
import com.edonoxako.easytranslate.di.module.ThreadMaanagmentModule;
import com.edonoxako.easytranslate.di.module.InteractorModule;
import com.edonoxako.easytranslate.ui.translator.TranslatorPresenter;

import dagger.Component;

/**
 * Created by eugene on 02.11.17.
 */
@Component(modules = {InteractorModule.class, ExternalApiModule.class, ThreadMaanagmentModule.class})
public interface TranslatorComponent {
    void inject(TranslatorPresenter presenter);
}
