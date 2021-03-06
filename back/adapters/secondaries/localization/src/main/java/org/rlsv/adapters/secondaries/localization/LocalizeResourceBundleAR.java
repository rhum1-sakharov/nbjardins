package org.rlsv.adapters.secondaries.localization;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LocalizeResourceBundleAR extends AbstractLocalize implements LocalizeServicePT {

    private static final Logger LOG = LoggerFactory.getLogger(LocalizeResourceBundleAR.class);

    ResourceBundle rbFrench;
    ResourceBundle rbDefault;
    ResourceBundle rbEnglish;


    @Override
    public String getMsg(String key) {
        return getMsg(key, null, null);
    }


    @Override
    public String getMsg(String key, String... args) {
        return getMsg(key, null, args);
    }

    @Override
    public String getMsg(String key, Locale locale, String... args) {

        ResourceBundle resourceBundle = getResourceBundle(locale);

        return getString(resourceBundle, key, args);
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

    private String getString(ResourceBundle resourceBundle, String key, String... args) {

        if (ArrayUtils.isEmpty(args)) {
            return resourceBundle.getString(key);
        }
        try {

            String msg = resourceBundle.getString(key);
            return MessageFormat.format(msg, args);
        } catch (Exception ex) {
            LOG.error("msg key {} not translatable : {}  ", key, ex.getMessage());
        }

        return "";
    }

    private ResourceBundle getResourceBundle(Locale locale) {

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

        return resourceBundle;

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
