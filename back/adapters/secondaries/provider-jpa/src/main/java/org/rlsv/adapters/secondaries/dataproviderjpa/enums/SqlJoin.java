package org.rlsv.adapters.secondaries.dataproviderjpa.enums;

import lombok.Getter;

public enum SqlJoin {

    JOIN("JOIN"),
    LEFT_JOIN("LEFT JOIN")
    ;


    @Getter
    private final String value;

    SqlJoin(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
