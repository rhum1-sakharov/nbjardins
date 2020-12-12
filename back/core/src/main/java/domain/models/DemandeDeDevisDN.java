package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandeDeDevisDN extends EntityDN {

    PersonneDN asker;
    String sujet;
    String message;
    String application;
    PersonneDN worker;
}
