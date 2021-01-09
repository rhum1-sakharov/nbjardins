package domains.wrapper;

import domains.models.ApplicationDN;
import lombok.Getter;
import lombok.Setter;
import security.LoginManager;
import transactions.DataProviderManager;

import java.util.HashMap;
import java.util.Locale;

@Getter
@Setter
public class RequestMap extends HashMap {

    /**
     * {@see domains.models.DevisDN}
     */
    public static final String REQUEST_KEY_DEVIS = "REQUEST_KEY_DEVIS";

    /**
     * {@see enums.UNIQUE_CODE}
     */
    public static final String REQUEST_KEY_UNIQUECODE = "REQUEST_KEY_UNIQUECODE";

    /**
     * {@see domains.models.ClientDN}
     */
    public static final String REQUEST_KEY_CLIENT = "REQUEST_KEY_CLIENT";

    /**
     * {@see security.LoginManager}
     */
    public static final String REQUEST_KEY_LOGIN_MANAGER = "REQUEST_KEY_LOGIN_MANAGER";


    private DataProviderManager dataProviderManager;
    private ApplicationDN application;
    private Locale locale;
    private LoginManager loginManager;

    public RequestMap() {
    }

    public RequestMap(Locale locale, ApplicationDN application, DataProviderManager dataProviderManager) throws Exception {
        super();

        this.locale = locale;
        this.application = application;
        this.dataProviderManager = dataProviderManager;

    }

    /**
     * Initialiser une requestMap à partir d'une autre requestMap. On ne prend ici que la locale, l'application et le dataProviderManager
     *
     * @param requestMap
     * @return
     * @throws Exception
     */
    public static RequestMap init(RequestMap requestMap) throws Exception {
        RequestMap requestMap1 = new RequestMap(requestMap.getLocale(), requestMap.getApplication(), requestMap.getDataProviderManager());
        return requestMap1;
    }
}
