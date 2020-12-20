package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Personne client;


}
