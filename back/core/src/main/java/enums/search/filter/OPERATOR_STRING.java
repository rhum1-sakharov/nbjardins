package enums.search.filter;

import lombok.Getter;

public enum OPERATOR_STRING {

    CONTAINS("CONTAINS"),
    NOT_CONTAINS("NOT_CONTAINS"),
    EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS"),
    NOT_STARTS_WITH("NOTSTARTS_WITH"),
    STARTS_WITH("STARTS_WITH");

    @Getter
    String value;

    OPERATOR_STRING(String value) {
        this.value = value;
    }
}
