package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;

    @ManyToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;

    @Column(name = "SIRET")
    private String siret;

    @Column(name = "SIGNATURE")
    private String signature;


    public Client(Personne personne) {
        this.personne = personne;
    }
}
