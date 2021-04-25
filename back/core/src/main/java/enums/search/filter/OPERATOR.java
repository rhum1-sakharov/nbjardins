package enums.search.filter;

import lombok.Getter;

public enum OPERATOR {
    GT("GREATER_THAN"),
    GTE("GREATER_THAN_OR_EQUALS"),
    LT("LESS_THAN"),
    LTE("LESS_THAN_OR_EQUALS"),
    CONTAINS("CONTAINS"),
    STARTS_WITH("STARTS_WITH"),
    ID_IN("ID_IN");

    @Getter
    String value;

    OPERATOR(String value) {
        this.value = value;
    }
}
