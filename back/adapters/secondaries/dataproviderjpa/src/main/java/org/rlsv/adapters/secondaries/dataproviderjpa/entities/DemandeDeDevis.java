package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="demandes_de_devis")
public class DemandeDeDevis extends Entity {

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID_ASKER")
    private Personne asker;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID_WORKER")
    private Personne worker;

    @Column(name="SUJET")
    private String sujet;

    @Column(name="MESSAGE")
    private String message;

}
