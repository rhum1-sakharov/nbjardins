package domain.wrapper;

import domain.models.ApplicationDN;
import ports.transactions.TransactionManagerPT;

import java.util.HashMap;
import java.util.Locale;

public class RequestMap extends HashMap {

    public static final String KEY_APPLICATION = "KEY_APPLICATION";
    public static final String KEY_LOCALE = "KEY_LOCALE";
    public static final String KEY_TRANSACTION_MANAGER = "KEY_TRANSACTION_MANAGER";


    public RequestMap(Locale locale, ApplicationDN application, TransactionManagerPT transactionManager) {
        super();
        this.put(KEY_APPLICATION, application);
        this.put(KEY_LOCALE, locale);
        this.put(KEY_TRANSACTION_MANAGER, transactionManager);

    }
}
