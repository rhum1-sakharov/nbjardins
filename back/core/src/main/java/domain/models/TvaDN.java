package domain.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TvaDN extends Domain {

    private String nom;
    private BigDecimal taux;
}
