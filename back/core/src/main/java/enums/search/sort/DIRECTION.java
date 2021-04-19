package enums.search.sort;

import lombok.Getter;

public enum DIRECTION {
    ASC("ASCENDING"),
    DESC("DESCENDING");

    @Getter
    String value;

    DIRECTION(String value) {
        this.value = value;
    }
}
