package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="taxes")
public class Taxe extends Entity {

    @Column(name="NOM")
    private String nom;

    @Column(name="TAUX")
    private BigDecimal taux;

    @OneToMany(mappedBy = "taxe")
    private List<Artisan__Taxe> artisan__taxeList;



}
