package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="artisans")
public class Artisan extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;


    @ManyToOne
    @JoinColumn(name = "ID_TAXE")
    private Taxe taxe;


}
