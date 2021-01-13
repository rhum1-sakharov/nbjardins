package utils;

import domains.ApplicationDN;
import domains.Domain;
import domains.wrapper.ResponseDN;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public static <T extends Domain> ResponseDN<T> initResponse(T one) {

        ResponseDN<T> responseDN = new ResponseDN<>();
        responseDN.setOne(one);

        return responseDN;
    }


    public static <T extends Domain> ResponseDN<T> initResponse(String message, boolean precondition) {

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(message, precondition);

        return initResponse(preconditions);
    }

    public static <T extends Domain> ResponseDN<T> initResponse(Map<String, Boolean> preconditions) {
        ResponseDN<T> responseDN = new ResponseDN<>();

        if (Objects.nonNull(preconditions)) {
            for (Map.Entry<String, Boolean> entry : preconditions.entrySet()) {

                if (entry.getValue()) {
                    responseDN.addErrorMessage(entry.getKey());
                }
            }
        }


        return responseDN;
    }


    public static ApplicationDN initApplication(String appToken) {
        ApplicationDN application = new ApplicationDN();
        application.setToken(appToken);
        return application;
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }


}
