package domain.enums;

import lombok.Getter;

public enum ROLES {
    ROLE_CLIENT("ROLE_CLIENT"),
    ROLE_ARTISAN("ROLE_ARTISAN");

    @Getter
    String value;

    ROLES(String value) {
        this.value = value;
    }
}
