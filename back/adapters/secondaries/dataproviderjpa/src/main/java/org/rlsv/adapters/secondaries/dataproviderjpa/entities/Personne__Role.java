package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="personnes__roles")
public class Personne__Role extends Entity {

    @ManyToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;

    @ManyToOne
    @JoinColumn(name = "ID_ROLE")
    private Role role;

}
