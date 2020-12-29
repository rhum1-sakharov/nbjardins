package domain.models;

import domain.enums.STATUT_DEVIS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisDN extends Domain {

    PersonneDN asker;
    PersonneDN worker;
    String sujet;
    String message;
    String numeroDevis;
    String lieu;
    Date dateDemande;
    Date dateEnCours;
    Date dateEnvoye;
    Date dateCreation;
    BigDecimal totalHT;
    BigDecimal tva;
    String remarque;
    String nota;
    String conditionDeReglement;
    String ordre;
    List<LigneDevisDN> ligneDevisList;
    STATUT_DEVIS statut;
    String iban;
    String rib;
    String logo;
    BigDecimal provision;
    String signature;
}
