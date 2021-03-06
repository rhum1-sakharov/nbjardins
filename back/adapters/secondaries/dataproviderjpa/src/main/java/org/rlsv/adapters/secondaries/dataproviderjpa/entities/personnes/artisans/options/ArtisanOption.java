package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.options;

import enums.MODELE_OPTION;
import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="artisans_options")
public class ArtisanOption extends org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity {

    @OneToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;

    @Enumerated(value = EnumType.STRING)
    @Column(name="OPTION")
    private MODELE_OPTION option;

    @Column(name="ACTIF")
    private boolean actif;


}
