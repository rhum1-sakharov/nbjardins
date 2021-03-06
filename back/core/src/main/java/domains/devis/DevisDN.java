package domains.devis;

import domains.Domain;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
import enums.STATUT_DEVIS;
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

    ClientDN client;
    ArtisanDN artisan;
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
    STATUT_DEVIS statut;
    String iban;
    String rib;
    String logo;
    BigDecimal provision;
    String signature;
    List<DevisLigneDN> devisLigneList;
    int validiteDevisMois;

}
