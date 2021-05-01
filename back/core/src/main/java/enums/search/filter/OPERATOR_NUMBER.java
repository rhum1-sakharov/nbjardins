package enums.search.filter;

import lombok.Getter;

public enum OPERATOR_NUMBER {

    GT("GREATER_THAN"),
    GTE("GREATER_THAN_OR_EQUALS"),
    LT("LESS_THAN"),
    LTE("LESS_THAN_OR_EQUALS"),
    BETWEEN_INCLUSIVE("BETWEEN_INCLUSIVE"),
    BETWEEN_EXCLUSIVE("BETWEEN_EXCLUSIVE"),
    IN("IN"),
    EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS");


    @Getter
    String value;

    OPERATOR_NUMBER(String value) {
        this.value = value;
    }
}
