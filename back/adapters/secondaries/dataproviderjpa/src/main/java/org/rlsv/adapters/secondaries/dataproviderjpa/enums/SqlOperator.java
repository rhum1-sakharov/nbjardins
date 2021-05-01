package org.rlsv.adapters.secondaries.dataproviderjpa.enums;

import lombok.Getter;

public enum SqlOperator {
    EQUALS("="),
    NOT_EQUALS("<>"),
    LIKE("LIKE"),
    IN("IN"),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<=");


    @Getter
    private final String value;

    SqlOperator(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
