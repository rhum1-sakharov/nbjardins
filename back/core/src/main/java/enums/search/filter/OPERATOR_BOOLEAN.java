package enums.search.filter;

import lombok.Getter;

public enum OPERATOR_BOOLEAN {

    EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS");

    @Getter
    String value;

    OPERATOR_BOOLEAN(String value) {
        this.value = value;
    }
}
