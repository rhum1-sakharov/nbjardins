package models;

import exceptions.TechnicalException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static localizations.MessageKeys.SERVER_ERROR;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Precondition {

    private String message;
    private boolean condition;
    public static final int INDEX_NOT_FOUND = -1;

    public static Precondition init(String message, boolean condition) {
        return new Precondition(message, condition);
    }

    public static void validate(Precondition... preconditions) throws TechnicalException {

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

    /**
     * Recuperer l'index d'un argument qui est de type clazz
     * @param parameterTypes
     * @param clazz
     * @return {@value INDEX_NOT_FOUND} si pas d'index trouvé, sinon l'index
     */
    public static int getIndexClassType(Class<?>[] parameterTypes, Class clazz) {

        int i = 0;
        for (Class<?> aClass : parameterTypes) {
            if (aClass == clazz) {
                return i;
            }
            i++;
        }

        return INDEX_NOT_FOUND;
    }
}
