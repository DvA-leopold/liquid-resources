package com.liquidresources.game.model.i18n.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class I18NBundleManager {
    static {
        initialize();
    }

    public static String getString(String key) {
        return myBundle.get(key);
    }

    public static String getChainString(String key, String ...args) {
        return myBundle.format(key, args);
    }

    public static String getLanguageCode() {
        return languageArray.get(counter).getLanguageCode();
    }

    public static void changeLanguage() {
        counter = (counter >= Language.values().length - 1) ? 0 : counter + 1;
        changeLanguage(languageArray.get(counter));
    }

    public static void changeLanguage(Language newLanguage) {
        myBundle = I18NBundle.createBundle(
                Gdx.files.internal("i18n/Bundle"),
                new Locale(
                        newLanguage.getLanguageCode(),
                        newLanguage.getCountryCode(),
                        newLanguage.getLocaleVariant()
                )
        );
    }

    private static void initialize() {
        languageArray = new ArrayList<>(Language.values().length);
        Collections.addAll(languageArray, Language.values());
        myBundle = I18NBundle.createBundle(
                Gdx.files.internal("i18n/Bundle"),
                new Locale("en")
        );
    }


    static private ArrayList<Language> languageArray;

    static private int counter = 0;
    static private I18NBundle myBundle;
}
