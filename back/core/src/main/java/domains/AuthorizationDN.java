package domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizationDN extends Domain {

    private String email;
    private String nom;
    private String prenom;
    private String picture;
    private String token;
}
