package enums.search.filter;

import lombok.Getter;

public enum FILTER_BOOL_JOIN {

    AND("AND"),
    OR("OR");


    @Getter
    String value;

    FILTER_BOOL_JOIN(String value) {
        this.value = value;
    }
}
