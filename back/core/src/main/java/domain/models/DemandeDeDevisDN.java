package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandeDeDevisDN extends Domain {

    PersonneDN asker;
    PersonneDN worker;
    String sujet;
    String message;
}
