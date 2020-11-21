package com.nbjardins.domain.entityresponse;

import com.nbjardins.domain.entities.Entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Response<T extends Entity> {

    boolean isError;
    List<String> errorMessages;
    List<T> resultList;
    T one;
    String message;

   public List<String> addMessage(String message) {

        String[] messages = new String[1];
        messages[0]=message;

        return addMessage(messages);
    }

    public List<String> addMessage(String... messages) {
        List<String> messageList = new ArrayList<String>();

        if (messages != null) {
            messageList.addAll(Arrays.asList(messages));
        }

        return messageList;
    }

}
