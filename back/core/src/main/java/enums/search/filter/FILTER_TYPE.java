package enums.search.filter;

import lombok.Getter;

public enum FILTER_TYPE {

    BOOLEAN("BOOLEAN"),
    STRING("STRING"),
    DATE("DATE"),
    NUMBER("NUMBER");

    @Getter
    String value;

    FILTER_TYPE(String value) {
        this.value = value;
    }
}
