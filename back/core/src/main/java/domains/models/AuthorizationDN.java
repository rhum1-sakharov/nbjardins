package domains.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationDN extends Domain {

    private String email;
    private String token;
}
