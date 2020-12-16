package domain.exceptions;

import lombok.Getter;
import usecase.ports.localization.LocalizeServicePT;

import java.text.MessageFormat;

@Getter
public abstract class CleanException extends Exception {

    String msgKey;
    String[] args;

    public CleanException(String message, Throwable cause) {
        super(message, cause);
    }

    public CleanException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause);
        this.msgKey = msgKey;
        this.args = args;
    }

    public String displayMessage(LocalizeServicePT localizeService) {

        if (this.args != null && this.args.length > 0) {
            return MessageFormat.format(localizeService.getMsg(msgKey), args);
        }
        return localizeService.getMsg(msgKey);
    }

}
