package exceptions;

import lombok.Getter;
import ports.localization.LocalizeServicePT;

import java.text.MessageFormat;
import java.util.Locale;

@Getter
public abstract class CleanException extends Exception {

    String msgKey;
    String[] args;

    public CleanException(String message) {
        super(message);
    }

    public CleanException(Throwable t) {
        super(t);
    }

    public CleanException(String message, Throwable cause, String msgKey) {
        super(message, cause);
        this.msgKey = msgKey;

    }

    public CleanException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause);
        this.msgKey = msgKey;
        this.args = args;
    }

    public String displayMessage(LocalizeServicePT localizeService, Locale locale) {

        if (this.args != null && this.args.length > 0) {
            return MessageFormat.format(localizeService.getMsg(msgKey, locale), args);
        }
        return localizeService.getMsg(msgKey, locale);
    }

}
