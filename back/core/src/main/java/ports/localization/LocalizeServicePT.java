package ports.localization;

import java.util.Locale;

public interface LocalizeServicePT {

    String getMsg(String key);

    String getMsg(String key, String... args);

    String getMsg(String key, Locale locale, String... args);


    Locale getFrenchLocale();

    Locale getEnglishLocale();

    Locale getWorkerLocale();

}
