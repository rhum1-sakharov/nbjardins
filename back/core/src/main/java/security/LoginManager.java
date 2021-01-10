package security;

import enums.TYPES_PERSONNE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginManager {

    TYPES_PERSONNE typePersonne;


    /**
     * informations necessaires pour faire fonctionner le mécanisme d'authorization
     * par exemple oauth§2, saml, springsecurity...
     */
    Object authorizationSettings;

}
