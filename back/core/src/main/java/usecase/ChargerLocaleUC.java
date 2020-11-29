package usecase;

import java.util.Locale;
import java.util.ResourceBundle;

public final class ChargerLocaleUC {

    public ResourceBundle choisirBundle(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle;
    }

}
