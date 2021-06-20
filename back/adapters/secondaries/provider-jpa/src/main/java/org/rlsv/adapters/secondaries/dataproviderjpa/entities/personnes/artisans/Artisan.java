package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans;

import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.conditions.reglements.ConditionDeReglement;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.taxes.Taxe;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="artisans")
public class Artisan extends org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity {

    @OneToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;




    @ManyToOne
    @JoinColumn(name = "ID_TAXE")
    private Taxe taxe;

    @ManyToOne
    @JoinColumn(name = "ID_CONDITION_DE_REGLEMENT")
    private ConditionDeReglement conditionDeReglement;

    @Column(name="EMAIL_PRO")
    private String emailPro;

    @Column(name="LOGO")
    private String logo;

    @Column(name="PROVISION")
    private BigDecimal provision;

    @Column(name="SIGNATURE")
    private String signature;

    @Column(name="SIRET")
    private String siret;

    @Column(name="VALIDITE_DEVIS_MOIS")
    private int validiteDevisMois;

}
