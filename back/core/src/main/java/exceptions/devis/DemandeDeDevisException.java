package exceptions.devis;

import exceptions.CleanException;

public class DemandeDeDevisException extends CleanException {



    public DemandeDeDevisException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public DemandeDeDevisException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
