package com.edonoxako.easytranslate.core.model;

/**
 * Created by eugene on 03.11.17.
 */

public class Language {

    private String locale;
    private String name;

    public Language(String locale, String name) {
        this.locale = locale;
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (getLocale() != null ? !getLocale().equals(language.getLocale()) : language.getLocale() != null)
            return false;
        return getName() != null ? getName().equals(language.getName()) : language.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getLocale() != null ? getLocale().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
