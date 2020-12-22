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
@Table(name="artisans__taxes")
public class Artisan__Taxe extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;


    @ManyToOne
    @JoinColumn(name="ID_TAXE")
    private Taxe taxe;
}
