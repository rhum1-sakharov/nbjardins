package usecase.ports.localization;

import java.util.Locale;

public interface LocalizeServicePT {

    String getMsg(String key, Locale locale);

    String getMsg(String key);

    Locale getFrenchLocale();
    Locale getEnglishLocale();
    Locale getWorkerLocale();

}
