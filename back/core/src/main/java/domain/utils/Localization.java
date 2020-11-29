package domain.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public static void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String getMsg(String key) {
        return resourceBundle.getString(key);
    }

}
