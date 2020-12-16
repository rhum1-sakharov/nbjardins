package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="roles")
public class Role extends Entity {

    private String nom;
    private String description;

}
