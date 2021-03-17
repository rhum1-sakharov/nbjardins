package enums;

import lombok.Getter;

public enum STATUT_DEVIS {
    A_TRAITER("A_TRAITER"),
    TRAITE("TRAITE"),
    ACCEPTE("ACCEPTE"),
    REFUSE("REFUSE"),
    ABANDON("ABANDON");

    @Getter
    String value;

    STATUT_DEVIS(String value) {
        this.value = value;
    }
}
