package org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis;

import enums.STATUT_DEVIS;
import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients.Client;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="devis")
public class Devis extends org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity {

    @ManyToOne
    @JoinColumn(name = "ID_CLIENT")
    private Client client;

    @Column(name="CLIENT_NOM")
    private String clientNom;

    @Column(name="CLIENT_PRENOM")
    private String clientPrenom;

    @Column(name="CLIENT_ADRESSE")
    private String clientAdresse;

    @Column(name="CLIENT_VILLE")
    private String clientVille;

    @Column(name="CLIENT_CODE_POSTAL")
    private String clientCodePostal;

    @Column(name="CLIENT_TELEPHONE")
    private String clientTelephone;

    @Column(name="CLIENT_EMAIL")
    private String clientEmail;

    @Column(name="CLIENT_SIGNATURE")
    private String clientSignature;

    @Column(name="CLIENT_SIRET")
    private String clientSiret;

    @Column(name="CLIENT_SOCIETE")
    private String clientSociete;

    @Column(name="CLIENT_FONCTION")
    private String clientFonction;

    @ManyToOne
    @JoinColumn(name= "ID_ARTISAN")
    private Artisan artisan;

    @Column(name="ARTISAN_LOGO")
    private String artisanLogo;

    @Column(name="ARTISAN_SIRET")
    private String artisanSiret;

    @Column(name="ARTISAN_SOCIETE")
    private String artisanSociete;

    @Column(name="ARTISAN_FONCTION")
    private String artisanFonction;

    @Column(name="ARTISAN_ADRESSE")
    private String artisanAdresse;

    @Column(name="ARTISAN_VILLE")
    private String artisanVille;

    @Column(name="ARTISAN_CODE_POSTAL")
    private String artisanCodePostal;

    @Column(name="ARTISAN_TELEPHONE")
    private String artisanTelephone;

    @Column(name="ARTISAN_EMAIL")
    private String artisanEmail;

    @Column(name="ARTISAN_SIGNATURE")
    private String artisanSignature;



    @Column(name="DATE_A_TRAITER")
    private Date dateATraiter;


    @Column(name="DATE_TRAITE")
    private Date dateTraite;


    @Column(name="DATE_ACCEPTE")
    private Date dateAccepte;


    @Column(name="DATE_REFUSE")
    private Date dateRefuse;


    @Column(name="DATE_ABANDON")
    private Date dateAbandon;


    @Column(name="SUJET")
    private String sujet;


    @Column(name="NUMERO_DEVIS")
    private String numeroDevis;


    @Column(name="LIEU")
    private String lieu;

    @Column(name="TOTAL_HT")
    private BigDecimal totalHT;


    @Column(name="TVA")
    private BigDecimal tva;

    @Column(name="REMARQUE")
    private String remarque;

    @Column(name="CONDITION_DE_REGLEMENT")
    private String conditionDeReglement;

    @Column(name="ORDRE")
    private String ordre;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUT")
    private STATUT_DEVIS statut;

    @Column(name="BANQUE")
    private String banque;

    @Column(name="IBAN")
    private String iban;

    @Column(name="RIB")
    private String rib;

    @Column(name="PROVISION")
    private BigDecimal provision;


    @Column(name="VALIDITE_DEVIS_MOIS")
    private int validiteDevisMois;

}
