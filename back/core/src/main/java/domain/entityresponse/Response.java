package domain.entityresponse;

import domain.entities.Entity;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Response<T extends Entity> {

    boolean isError = false;
    List<String> errorMessages = new ArrayList<>();
    List<T> resultList = new ArrayList<>();
    T one;
    String message = "";

    public void addErrorMessage(String message) {

        String[] messages = new String[1];
        messages[0] = message;

        addErrorMessage(messages);
    }

    public void addErrorMessage(String... messages) {

        if (messages != null) {
            errorMessages.addAll(Arrays.asList(messages));
        }
    }

    public void addErrorMessage(List<String> messages) {

        if (CollectionUtils.isNotEmpty(messages)) {
            errorMessages.addAll(messages);
        }
    }

}
