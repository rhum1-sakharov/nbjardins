package org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.roles;

import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.roles.Personne__Role;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="roles")
public class Role extends Entity {

    private String nom;
    private String description;

    @OneToMany(mappedBy = "role")
    private List<Personne__Role> personne__roleList;

}
