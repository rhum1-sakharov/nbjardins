package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="personnes")
public class Personne extends Entity {

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String ville;
    private String codePostal;
    private String fonction;
    private String societe;
    private String email;

    @OneToOne(mappedBy = "worker")
    private Application application;

}
