package domain.exceptions;

public class DemandeDeDevisException extends CleanException {


    public DemandeDeDevisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemandeDeDevisException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public DemandeDeDevisException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
