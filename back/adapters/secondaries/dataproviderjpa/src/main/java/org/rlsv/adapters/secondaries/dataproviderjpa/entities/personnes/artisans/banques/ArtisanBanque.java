package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.banques;

import lombok.Getter;
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
@Table(name = "artisans_banques")
public class ArtisanBanque extends Entity {

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "RIB")
    private String rib;

    @ManyToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;

    @Column(name = "PREFERE")
    private boolean prefere;

}
