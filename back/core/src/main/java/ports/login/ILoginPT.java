package ports.login;

import domains.authorizations.AuthorizationDN;
import exceptions.LoginException;
import security.LoginManager;

import java.util.List;

public interface ILoginPT {

    AuthorizationDN getAuthorization(LoginManager loginManager) throws LoginException;

    String redirectToThirdServerAuthorization(LoginManager loginManager) throws LoginException;

    String generateToken(LoginManager loginManager,AuthorizationDN authorization, List<String> roles);


    String getUserName(String token) throws LoginException;
}
