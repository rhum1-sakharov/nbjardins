package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
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

    @OneToOne(mappedBy = "artisan")
    private Artisan__Taxe artisan__taxe;


}
