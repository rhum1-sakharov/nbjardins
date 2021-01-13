package models;

import exceptions.CleanException;
import exceptions.TechnicalException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static localizations.MessageKeys.SERVER_ERROR;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Precondition {

    String message;
    boolean condition;

    public static Precondition init(String message, boolean condition) {
        return new Precondition(message, condition);
    }

    public static void validate(Precondition... preconditions) throws CleanException {

        boolean isError = false;
        StringBuilder sb = new StringBuilder();

        for (Precondition precondition : preconditions) {
            if (!precondition.isCondition()) {
                isError = true;
                sb.append(precondition.getMessage()).append(System.lineSeparator());
            }
        }

        if (isError) {
            throw new TechnicalException(sb.toString(), null, SERVER_ERROR, new String[]{""});
        }

    }
}
