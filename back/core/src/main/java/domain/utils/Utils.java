package domain.utils;

import domain.entities.Entity;
import domain.entityresponse.Response;

import java.util.*;

public class Utils {



    public static <T extends Entity> Response<T> initResponse(String message, boolean precondition) {

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(message, precondition);

        return initResponse(preconditions);
    }

    public static <T extends Entity> Response<T> initResponse(Map<String, Boolean> preconditions) {
        Response<T> response = new Response<>();

        if (Objects.nonNull(preconditions)) {
            for (Map.Entry<String, Boolean> entry : preconditions.entrySet()) {

                if (entry.getValue()) {
                    response.setError(true);
                    response.addErrorMessage(entry.getKey());
                }
            }
        }


        return response;
    }



}
