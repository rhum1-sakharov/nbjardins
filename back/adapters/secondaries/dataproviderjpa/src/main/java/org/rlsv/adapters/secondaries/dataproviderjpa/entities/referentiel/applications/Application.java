package org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.applications;

import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="applications")
public class Application extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_ARTISAN")
    private Artisan artisan;

    @Column(name="NOM")
    private String nom;

    @Column(name="TOKEN")
    private String token;

    @Column(name="SITE")
    private String site;

}
