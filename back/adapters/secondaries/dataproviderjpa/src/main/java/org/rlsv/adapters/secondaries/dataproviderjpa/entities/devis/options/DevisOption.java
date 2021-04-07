package org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.options;

import enums.MODELE_OPTION;
import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "devis_options")
public class DevisOption extends org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity {

    @ManyToOne
    @JoinColumn(name = "ID_DEVIS")
    private Devis devis;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MODELE_OPTION")
    private MODELE_OPTION modeleOption;


    @Column(name = "ACTIF")
    private Boolean actif;

}
