package domain.exceptions;

public class PersistenceException extends CleanException{


    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
