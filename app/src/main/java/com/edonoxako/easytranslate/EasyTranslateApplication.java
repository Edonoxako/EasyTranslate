package com.edonoxako.easytranslate;

import android.app.Application;

import com.edonoxako.easytranslate.di.component.DaggerTranslatorComponent;
import com.edonoxako.easytranslate.di.component.TranslatorComponent;
import com.edonoxako.easytranslate.di.module.ExternalApiModule;
import com.edonoxako.easytranslate.di.module.InteractorModule;
import com.edonoxako.easytranslate.di.module.ThreadManagementModule;

/**
 * Created by eugene on 02.11.17.
 */

public class EasyTranslateApplication extends Application {

    private static TranslatorComponent translatorComponent;

    public static TranslatorComponent getTranslatorComponent() {
        return translatorComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        translatorComponent = buildComponent();
    }

    private TranslatorComponent buildComponent() {
        return DaggerTranslatorComponent.builder()
                .interactorModule(new InteractorModule())
                .externalApiModule(new ExternalApiModule(this))
                .threadManagementModule(new ThreadManagementModule())
                .build();
    }
}
