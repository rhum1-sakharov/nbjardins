package domain.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TaxeDN extends Domain {


    private String nom;
    private BigDecimal taux;


}