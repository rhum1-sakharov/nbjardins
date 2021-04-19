package org.rlsv.adapters.secondaries.dataproviderjpa.entities.produits;

import domains.referentiel.taxes.TaxeDN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@javax.persistence.Entity
@Table(name = "produits")
public class Produit extends Entity {

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_TAXE")
    private TaxeDN taxe;

    @Column(name="PRIX_UNITAIRE_HT")
    private BigDecimal prixUnitaireHT;

}
