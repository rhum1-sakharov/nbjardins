package org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.lignes;

import lombok.Getter;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@javax.persistence.Entity
@Table(name="devis_lignes")
public class DevisLigne extends Entity {

    @ManyToOne
    @JoinColumn(name = "ID_DEVIS")
    private Devis devis;


    @Column(name="DESIGNATION")
    private String designation;

    @Column(name="MONTANT_HT")
    private BigDecimal montantHT;



}
