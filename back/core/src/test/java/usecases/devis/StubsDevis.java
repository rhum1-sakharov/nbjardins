package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import enums.MODELE_OPTION;
import enums.STATUT_DEVIS;

import java.util.Date;

public class StubsDevis {

    public static final String ARTISAN_EMAIL = "artisan@test.fr";

    public static DevisDN devisATraiter(ArtisanDN artisan) {

        DevisDN devisDN = new DevisDN();
        devisDN.setId("1");
        devisDN.setDateATraiter(new Date());
        devisDN.setStatut(STATUT_DEVIS.A_TRAITER);

        devisDN.setArtisan(artisan);

        return devisDN;
    }

    public static ArtisanDN artisan(PersonneDN personne, ConditionDeReglementDN conditionDeReglement){

        ArtisanDN artisan = new ArtisanDN();
        artisan.setId("1");

        artisan.setPersonne(personne);
        artisan.setConditionDeReglement(conditionDeReglement);

        return artisan;
    }

    public static ConditionDeReglementDN conditionDeReglement(){
        ConditionDeReglementDN cd = new ConditionDeReglementDN();
        cd.setCondition("à réception de la facture");
        cd.setId("1");
        return cd;
    }

    public static PersonneDN personne(){

        PersonneDN personne = new PersonneDN();
        personne.setAdresse("366 route de saint mathieu");
        personne.setCodePostal("84210");
        personne.setEmail("artisan@test.fr");
        personne.setFonction("jardinier");
        personne.setSociete("la mélie");
        personne.setNumeroTelephone("0612345678");
        personne.setVille("les matelles");

        return personne;
    }

    public static DevisOptionDN option(MODELE_OPTION modeleOption, boolean actif, DevisDN devis) {
        DevisOptionDN devisOption = new DevisOptionDN();
        devisOption.setModeleOption(modeleOption);
        devisOption.setActif(actif);
        devisOption.setDevis(devis);
        return devisOption;
    }

}
