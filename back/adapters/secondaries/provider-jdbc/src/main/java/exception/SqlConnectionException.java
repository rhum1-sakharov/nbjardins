package exception;

import exceptions.CleanException;

public class SqlConnectionException  extends CleanException {

    public SqlConnectionException(Throwable t){
        super(t);
    }
    public SqlConnectionException(String msg){
        super(msg);
    }


    public SqlConnectionException(String message, Throwable cause, String msgKey) {
        super(message, cause, msgKey);
    }

    public SqlConnectionException(String message, Throwable cause, String msgKey, String[] args) {
        super(message, cause, msgKey, args);
    }
}
