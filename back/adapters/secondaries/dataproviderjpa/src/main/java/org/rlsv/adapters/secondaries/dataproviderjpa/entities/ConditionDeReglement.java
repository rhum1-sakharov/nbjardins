package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="conditions_de_reglement")
public class ConditionDeReglement extends Entity {

    @Column(name = "CONDITION")
    private String condition;

}
