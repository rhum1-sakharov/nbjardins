package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="conditions_de_reglement")
public class ConditionDeReglement extends Entity {

    @Column(name = "LIBELLE")
    private String condition;

    @OneToMany(mappedBy = "conditionDeReglement")
    private List<Artisan> artisanList = new ArrayList<>();

}
