package domains.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ArtisanDN extends Domain {

    private PersonneDN personne;
    private TaxeDN taxe;
    private ConditionDeReglementDN conditionDeReglement;
    private String logo;
    private BigDecimal provision;
    private String signature;
    private ApplicationDN application;
    private String siret;
    private int validiteDevisMois;

}
