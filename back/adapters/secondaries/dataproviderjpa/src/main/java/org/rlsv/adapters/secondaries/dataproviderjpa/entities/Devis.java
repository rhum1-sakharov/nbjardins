package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import domain.enums.STATUT_DEVIS;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Column(name="STATUT")
    private STATUT_DEVIS statut;

}
