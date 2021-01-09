package ports.login;

import domains.models.AuthorizationDN;
import domains.wrapper.ResponseDN;
import security.LoginManager;

public interface ILoginPT {

    ResponseDN<AuthorizationDN> getAuthorization(LoginManager loginManager);

}
