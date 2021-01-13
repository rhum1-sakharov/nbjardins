package exceptions;

public class MailException extends CleanException{

    public MailException(String message) {
        super(message);
    }


    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public MailException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
