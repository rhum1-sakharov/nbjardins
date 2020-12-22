package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import domain.enums.STATUT_DEVIS;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="devis")
public class Devis extends Entity {

    @ManyToOne
    @JoinColumn(name = "ID_ASKER")
    private Personne asker;

    @ManyToOne
    @JoinColumn(name= "ID_WORKER")
    private Personne worker;

    @Column(name="SUJET")
    private String sujet;

    @Column(name="MESSAGE")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUT")
    private STATUT_DEVIS statut;

    @Column(name="TVA")
    private BigDecimal tva;

    @Column(name="NUMERO_DEVIS")
    private String numeroDevis;

    @Column(name="DATE_CREATION")
    private Date dateCreation;

}
