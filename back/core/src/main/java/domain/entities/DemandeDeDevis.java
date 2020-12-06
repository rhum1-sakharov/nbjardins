package domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandeDeDevis extends Entity {

    Personne asker;
    String sujet;
    String message;
    String application;
    Personne worker;
}
