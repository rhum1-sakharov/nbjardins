package utils;

import domains.models.Domain;
import domains.wrapper.ResponseDN;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {


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



}
