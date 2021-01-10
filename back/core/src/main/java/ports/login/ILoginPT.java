package ports.login;

import domains.models.AuthorizationDN;
import exceptions.LoginException;
import security.LoginManager;

public interface ILoginPT {

    AuthorizationDN getAuthorization(LoginManager loginManager) throws  LoginException;

    String redirectToThirdServerAuthorization(LoginManager loginManager) throws LoginException;


}
