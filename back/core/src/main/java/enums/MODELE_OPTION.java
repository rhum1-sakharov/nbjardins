package enums;

import lombok.Getter;

public enum MODELE_OPTION {
    TVA_SAISISSABLE_PAR_LIGNE("TVA_SAISISSABLE_PAR_LIGNE"),
    COLONNE_QUANTITE("COLONNE_QUANTITE"),
    COORDONNEES_BANQUAIRES("COORDONNEES_BANQUAIRES"),
    PROVISION("PROVISION");

    @Getter
    String value;

    MODELE_OPTION(String value) {
        this.value = value;
    }
}
