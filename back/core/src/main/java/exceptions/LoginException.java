package exceptions;

public class LoginException extends CleanException{

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public LoginException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
