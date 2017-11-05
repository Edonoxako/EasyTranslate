package com.edonoxako.easytranslate.ui.translator;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.edonoxako.easytranslate.EasyTranslateApplication;
import com.edonoxako.easytranslate.R;
import com.edonoxako.easytranslate.core.model.Language;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.Flowable;

public class MainActivity extends MvpAppCompatActivity implements TranslatorView {

    @BindView(R.id.spinnerOriginLanguage)
    Spinner spinnerOriginLanguage;

    @BindView(R.id.spinnerTranslationLanguage)
    Spinner spinnerTranslationLanguage;

    @BindView(R.id.viewEditTextToTranslate)
    EditText editTextToTranslate;

    @BindView(R.id.buttonSwapLanguages)
    ImageButton buttonSwapLanguages;

    @BindView(R.id.buttonTranslate)
    Button buttonTranslate;

    @BindView(R.id.viewTranslatedText)
    TextView viewTranslatedText;

    @InjectPresenter
    public TranslatorPresenter presenter;

    private List<Language> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EasyTranslateApplication.getTranslatorComponent().inject(presenter);
    }

    @OnClick(R.id.buttonTranslate)
    public void onTranslateClick() {
        buttonTranslate.requestFocus();
        hideKeyboard();

        String textToTranslate = editTextToTranslate.getText().toString();
        presenter.translate(textToTranslate);
    }

    @OnItemSelected(R.id.spinnerOriginLanguage)
    public void onSetOriginLanguage(int position) {
        presenter.setOriginLanguage(languages.get(position));
    }

    @OnItemSelected(R.id.spinnerTranslationLanguage)
    public void onSetTranslationLanguage(int position) {
        presenter.setTranslationLanguage(languages.get(position));
    }

    @Override
    public void setSupportedLanguages(List<Language> languages) {
        this.languages = languages;

        String[] languageStrings = Flowable.fromIterable(languages)
                .map(Language::getName)
                .toList()
                .blockingGet()
                .toArray(new String[]{});

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                languageStrings
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOriginLanguage.setAdapter(spinnerAdapter);
        spinnerTranslationLanguage.setAdapter(spinnerAdapter);

        presenter.setOriginLanguage(languages.get(0));
        spinnerOriginLanguage.setSelection(0);

        presenter.setTranslationLanguage(languages.get(1));
        spinnerTranslationLanguage.setSelection(1);
    }

    @Override
    public void setOriginLanguage(Language language) {
        int index = getLanguageIndex(language);
        spinnerOriginLanguage.setSelection(index);
    }

    @Override
    public void setTranslationLanguage(Language language) {
        int index = getLanguageIndex(language);
        spinnerTranslationLanguage.setSelection(index);
    }

    @Override
    public void setTranslatedText(String translatedText) {
        viewTranslatedText.setText(translatedText);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private int getLanguageIndex(Language language) {
        int index = 0;
        if (languages != null) {
            index = languages.lastIndexOf(language);
        }
        return index;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextToTranslate.getWindowToken(), 0);
    }

}
