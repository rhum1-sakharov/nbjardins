package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="personnes")
public class Personne extends Entity {

    private String nom;
    private String prenom;

    @Column(name="NUMERO_TELEPHONE")
    private String numeroTelephone;

    private String adresse;
    private String ville;
    private String codePostal;
    private String fonction;
    private String societe;
    private String email;



    @OneToOne(mappedBy = "personne")
    private Artisan artisan;

    @OneToOne(mappedBy = "personne")
    private Client client;

    @OneToMany(mappedBy = "personne")
    private List<Personne__Role> personne__roleList;

}
