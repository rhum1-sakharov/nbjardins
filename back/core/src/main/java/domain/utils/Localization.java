package domain.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Localization {

    public static ResourceBundle rbFrench;
    public static ResourceBundle rbDefault;
    public static ResourceBundle rbEnglish;

    public static String getMsg(String key, Locale locale) {

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


    /**
     * On charge le resourceBundle s'il n'est pas déjà chargé
     *
     * @param resourceBundle
     * @param locale
     * @return
     */
    private static ResourceBundle loadResourceBundle(ResourceBundle resourceBundle, Locale locale) {

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
