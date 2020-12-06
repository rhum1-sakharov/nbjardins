package com.nbjardins.adapters.secondaries.localization;

import usecase.ports.localization.LocalizeServicePT;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LocalizeResourceBundleAR extends AbstractLocalize implements LocalizeServicePT {

    ResourceBundle rbFrench;
    ResourceBundle rbDefault;
    ResourceBundle rbEnglish;




    @Override
    public String getMsg(String key, Locale locale) {

        ResourceBundle resourceBundle;

        if (Objects.isNull(locale)) {
            resourceBundle = loadResourceBundle(rbDefault, null);
        } else {
            switch (locale.getLanguage()) {
                case "fr":
                    rbFrench = loadResourceBundle(rbFrench, locale);
                    resourceBundle = rbFrench;
                    break;
                case "en":
                    rbEnglish = loadResourceBundle(rbEnglish, locale);
                    resourceBundle = rbEnglish;
                    break;
                default:
                    rbDefault = loadResourceBundle(rbDefault, locale);
                    resourceBundle = rbDefault;
                    break;
            }
        }

        return resourceBundle.getString(key);

    }

    @Override
    public String getMsg(String key) {
        return getMsg(key, null);
    }

    @Override
    public Locale getFrenchLocale() {
        return frLocale;
    }

    @Override
    public Locale getEnglishLocale() {
        return enLocale;
    }

    @Override
    public Locale getWorkerLocale() {
        return workerLocale;
    }


    /**
     * On charge le resourceBundle s'il n'est pas déjà chargé
     *
     * @param resourceBundle
     * @param locale
     * @return
     */
    private ResourceBundle loadResourceBundle(ResourceBundle resourceBundle, Locale locale) {

        if (Objects.isNull(resourceBundle)) {

            if (Objects.isNull(locale)) {
                resourceBundle = ResourceBundle.getBundle("messages");
            } else {
                resourceBundle = ResourceBundle.getBundle("messages", locale);
            }
        }

        return resourceBundle;
    }
}
