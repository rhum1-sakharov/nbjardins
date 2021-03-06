package org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="clients")
public class Client extends Entity {

    @OneToOne
    @JoinColumn(name = "ID_PERSONNE")
    private Personne personne;


}
