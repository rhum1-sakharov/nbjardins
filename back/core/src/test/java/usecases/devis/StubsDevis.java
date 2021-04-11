package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.artisans.options.ArtisanOptionDN;
import domains.personnes.clients.ClientDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import domains.referentiel.taxes.TaxeDN;
import enums.MODELE_OPTION;
import enums.STATUT_DEVIS;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StubsDevis {



    public static DevisDN devisATraiter(ArtisanDN artisan, ArtisanBanqueDN artisanBanque, ClientDN client, String sujet, String numeroDevis) {

        DevisDN devis = new DevisDN();
        devis.setId("1");
        devis.setDateATraiter(LocalDate.now());
        devis.setStatut(STATUT_DEVIS.A_TRAITER);
        devis.setSujet(sujet);

        devis.setArtisan(artisan);
        devis.setArtisanLogo(artisan.getLogo());
        devis.setArtisanSignature(artisan.getSignature());
        devis.setArtisanVille(artisan.getPersonne().getVille());
        devis.setArtisanTelephone(artisan.getPersonne().getNumeroTelephone());
        devis.setArtisanSociete(artisan.getPersonne().getSociete());
        devis.setArtisanSiret(artisan.getSiret());
        devis.setArtisanFonction(artisan.getPersonne().getFonction());
        devis.setArtisanEmail(artisan.getEmailPro());
        devis.setArtisanCodePostal(artisan.getPersonne().getCodePostal());
        devis.setArtisanAdresse(artisan.getPersonne().getAdresse());

        devis.setClient(client);
        devis.setClientSignature(null);
        devis.setClientVille(client.getPersonne().getVille());
        devis.setClientTelephone(client.getPersonne().getNumeroTelephone());
        devis.setClientSociete(client.getPersonne().getSociete());
        devis.setClientSiret(null);
        devis.setClientFonction(client.getPersonne().getFonction());
        devis.setClientEmail(client.getPersonne().getEmail());
        devis.setClientCodePostal(client.getPersonne().getCodePostal());
        devis.setClientAdresse(client.getPersonne().getAdresse());

        devis.setLieu(artisan.getPersonne().getVille());
        devis.setValiditeDevisMois(artisan.getValiditeDevisMois());

        devis.setProvision(artisan.getProvision());
        devis.setConditionDeReglement(artisan.getConditionDeReglement().getCondition());
        devis.setNumeroDevis(numeroDevis);
        devis.setTva(artisan.getTaxe().getTaux());


        devis.setRib(artisanBanque.getRib());
        devis.setIban(artisanBanque.getIban());
        devis.setBanque(artisanBanque.getBanque());

        return devis;
    }

    public static ClientDN client1() {
        ClientDN clientDN = new ClientDN();

        clientDN.setPersonne(personneClient1());
        clientDN.setSiret(null);
        clientDN.setSignature(null);
        clientDN.setId("1");


        return clientDN;

    }

    public static ArtisanBanqueDN artisanBanque(String id, ArtisanDN artisan, String rib, String iban, String banque) {

        ArtisanBanqueDN artisanBanqueDN = new ArtisanBanqueDN();

        artisanBanqueDN.setIban(iban);
        artisanBanqueDN.setRib(rib);
        artisanBanqueDN.setArtisan(artisan);
        artisanBanqueDN.setBanque(banque);
        artisanBanqueDN.setId(id);

        return artisanBanqueDN;
    }

    public static ArtisanDN artisan( ConditionDeReglementDN conditionDeReglement, TaxeDN taxe) {

        ArtisanDN artisan = new ArtisanDN();
        artisan.setId("1");
        artisan.setSiret("404 471 252 00027");
        artisan.setPersonne(personneArtisan1());
        artisan.setConditionDeReglement(conditionDeReglement);
        artisan.setProvision(new BigDecimal(33.2));
        artisan.setTaxe(taxe);

        artisan.setLogo("logo");
        artisan.setSignature("signatureArtisan");
        artisan.setValiditeDevisMois(2);
        artisan.setEmailPro("artisan@emailpro.fr");


        return artisan;
    }

    public static TaxeDN taxe() {

        TaxeDN taxeDN = new TaxeDN();

        taxeDN.setNom("TVA 5.5");
        taxeDN.setTaux(new BigDecimal(5.5));
        taxeDN.setId("1");

        return taxeDN;

    }

    public static ConditionDeReglementDN conditionDeReglement() {
        ConditionDeReglementDN cd = new ConditionDeReglementDN();
        cd.setCondition("à réception de la facture");
        cd.setId("1");
        return cd;
    }

    public static PersonneDN personneArtisan1() {

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

    public static PersonneDN personneClient1() {

        PersonneDN personne = new PersonneDN();
        personne.setAdresse("70 route de saint test");
        personne.setCodePostal("84810");
        personne.setEmail("client@test.fr");
        personne.setNumeroTelephone("0645558798");
        personne.setVille("saint-didier");

        return personne;
    }

    public static DevisOptionDN option(MODELE_OPTION modeleOption, boolean actif, DevisDN devis) {
        DevisOptionDN devisOption = new DevisOptionDN();
        devisOption.setModeleOption(modeleOption);
        devisOption.setActif(actif);
        devisOption.setDevis(devis);
        return devisOption;
    }

    public static List<ArtisanOptionDN> artisanOptionList(ArtisanDN artisan) {

        List<ArtisanOptionDN> artisanOptionList = new ArrayList<>();

        ArtisanOptionDN ao1 = new ArtisanOptionDN(artisan,MODELE_OPTION.COLONNE_QUANTITE,false);
        artisanOptionList.add(ao1);

        ArtisanOptionDN ao2 = new ArtisanOptionDN(artisan,MODELE_OPTION.COORDONNEES_BANQUAIRES,false);
        artisanOptionList.add(ao2);

        ArtisanOptionDN ao3 = new ArtisanOptionDN(artisan,MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE,false);
        artisanOptionList.add(ao3);

        ArtisanOptionDN ao4 = new ArtisanOptionDN(artisan,MODELE_OPTION.PROVISION,true);
        artisanOptionList.add(ao4);



        return artisanOptionList;

    }
}
