package enums.search.filter;

import lombok.Getter;

public enum FILTER_JOIN {

    AND("AND"),
    OR("OR");

    @Getter
    String value;

    FILTER_JOIN(String value) {
        this.value = value;
    }
}
