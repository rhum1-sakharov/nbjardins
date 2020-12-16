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
@Table(name="applications")
public class Application extends Entity {

    @ManyToOne
    @JoinColumn(name = "ID_WORKER")
    private Personne worker;

    @Column(name="NOM")
    private String nom;

    @Column(name="TOKEN")
    private String token;

}
