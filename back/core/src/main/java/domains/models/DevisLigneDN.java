package domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisLigneDN extends Domain {

    String designation;
    BigDecimal montantHT;
}
