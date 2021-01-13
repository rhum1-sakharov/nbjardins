package exceptions;

public class PdfException extends CleanException{


    public PdfException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public PdfException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
