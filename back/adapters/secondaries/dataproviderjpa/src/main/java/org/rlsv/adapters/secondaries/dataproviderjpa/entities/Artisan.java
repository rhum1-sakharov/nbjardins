package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="artisans")
public class Artisan extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;

    @OneToOne
    @JoinColumn(name = "ID_APPLICATION")
    private Application application;


    @ManyToOne
    @JoinColumn(name = "ID_TAXE")
    private Taxe taxe;

    @ManyToOne
    @JoinColumn(name = "ID_CONDITION_DE_REGLEMENT")
    private ConditionDeReglement conditionDeReglement;

    @Column(name="LOGO")
    private String logo;

    @Column(name="PROVISION")
    private BigDecimal provision;

    @Column(name="SIGNATURE")
    private String signature;



}
