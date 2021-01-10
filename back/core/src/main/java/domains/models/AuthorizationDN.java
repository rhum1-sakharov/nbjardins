package domains.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizationDN extends Domain {

    private String email;
    private String token;
}
