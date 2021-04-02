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

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisDN extends Domain {

    ArtisanDN artisan;
    ClientDN client;

    String artisanLogo;
    String artisanSiret;
    String artisanSociete;
    String artisanFonction;
    String artisanAdresse;
    String artisanVille;
    String artisanCodePostal;
    String artisanTelephone;
    String artisanEmail;
    String artisanSignature;

    String clientNom;
    String clientPrenom;
    String clientAdresse;
    String clientVille;
    String clientCodePostal;
    String clientTelephone;
    String clientEmail;
    String clientSignature;
    String clientSiret;
    String clientSociete;
    String clientFonction;


    Date dateATraiter;
    Date dateTraite;
    Date dateAccepte;
    Date dateRefuse;
    Date dateAbandon;

    String sujet;
    String numeroDevis;
    String lieu;
    BigDecimal totalHT;
    BigDecimal tva;
    String remarque;
    String conditionDeReglement;
    String ordre;
    STATUT_DEVIS statut;
    String banque;
    String iban;
    String rib;
    BigDecimal provision;
    int validiteDevisMois;

}
