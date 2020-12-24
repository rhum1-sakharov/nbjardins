package domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtisanDN extends Domain {

    private PersonneDN artisan;
    private TaxeDN taxe;
    private ConditionDeReglementDN conditionDeReglement;

}
