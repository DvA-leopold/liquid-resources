package com.liquidresources.game.model.i18n.manager;

public enum Language {
    ENGLISH("en", "EN", "VAR1"),
    RUSSIAN("ru", "RU", "VAR1");

    Language(String languageCode, String countryCode, String localeVariant) {
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.localeVariant = localeVariant;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocaleVariant() {
        return localeVariant;
    }


    final private String languageCode, countryCode, localeVariant;
}
