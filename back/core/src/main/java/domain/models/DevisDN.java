package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisDN extends Domain {

    PersonneDN artisan;
    PersonneDN client;
    String sujet;
    String message;
    String numeroDevis;
    String lieu;
    Date dateCreation;
    BigDecimal totalHT;
    TvaDN tva;
    String remarque;
    String nota;
    String conditionDeReglement;
    String ordre;
    List<LigneDevisDN> ligneDevisList;

}
