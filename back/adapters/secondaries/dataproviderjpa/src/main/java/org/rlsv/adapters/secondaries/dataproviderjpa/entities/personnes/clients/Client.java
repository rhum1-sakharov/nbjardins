package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client extends Entity {


    @ManyToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "VILLE")
    private String ville;

    @Column(name = "CODE_POSTAL")
    private String codePostal;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SIGNATURE")
    private String signature;

    @Column(name = "SIRET")
    private String siret;

    @Column(name = "SOCIETE")
    private String societe;

    @Column(name = "FONCTION")
    private String fonction;

}
