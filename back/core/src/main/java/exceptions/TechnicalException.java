package exceptions;

public class TechnicalException extends CleanException{

    public TechnicalException(String message) {
        super(message);
    }


    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public TechnicalException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
