package ports.login;

import domains.AuthorizationDN;
import domains.PersonneDN;
import exceptions.LoginException;
import security.LoginManager;

import java.util.List;

public interface ILoginPT {

    AuthorizationDN getAuthorization(LoginManager loginManager) throws LoginException;

    String redirectToThirdServerAuthorization(LoginManager loginManager) throws LoginException;

    String generateToken(PersonneDN personne, List<String> roles);


    String getUserName(String token) throws LoginException;
}
