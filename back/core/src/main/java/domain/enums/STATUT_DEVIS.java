package domain.enums;

import lombok.Getter;

public enum STATUT_DEVIS {
    DEMANDE("DEMANDE"),
    TRAITEMENT_EN_COURS("TRAITEMENT_EN_COURS"),
    ENVOYE("ENVOYE"),
    PERDU("PERDU"),
    GAGNE("GAGNE");

    @Getter
    String value;

    STATUT_DEVIS(String value) {
        this.value = value;
    }
}
