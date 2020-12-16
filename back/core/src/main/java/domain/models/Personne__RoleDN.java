package domain.models;

import lombok.Data;

@Data
public class Personne__RoleDN extends Domain {

    private PersonneDN personne;
    private RoleDN role;
}
