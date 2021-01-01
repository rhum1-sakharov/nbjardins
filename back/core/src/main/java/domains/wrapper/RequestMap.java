package domains.wrapper;

import domains.models.ApplicationDN;
import lombok.Getter;
import lombok.Setter;
import transactions.DataProviderManager;

import java.util.HashMap;
import java.util.Locale;

@Getter
@Setter
public class RequestMap extends HashMap {

    /**
     * {@link domains.models.DevisDN}
     */
    public static final String REQUEST_KEY_DEVIS = "REQUEST_KEY_DEVIS";

    /**
     * {@link enums.UNIQUE_CODE}
      */
    public static final String REQUEST_KEY_UNIQUECODE = "REQUEST_KEY_UNIQUECODE";

    /**
     * {@link domains.models.ClientDN}
     */
    public static final String REQUEST_KEY_CLIENT = "REQUEST_KEY_CLIENT";

    private DataProviderManager dataProviderManager;
    private ApplicationDN application;
    private Locale locale;

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
     * @param requestMap
     * @return
     * @throws Exception
     */
    public static RequestMap init(RequestMap requestMap) throws Exception {
        RequestMap requestMap1 = new RequestMap(requestMap.getLocale(), requestMap.getApplication(), requestMap.getDataProviderManager());
        return requestMap1;
    }
}
