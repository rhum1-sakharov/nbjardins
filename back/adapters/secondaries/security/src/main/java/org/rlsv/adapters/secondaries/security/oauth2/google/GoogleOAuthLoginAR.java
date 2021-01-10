package org.rlsv.adapters.secondaries.security.oauth2.google;

import domains.models.AuthorizationDN;
import domains.wrapper.ResponseDN;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import ports.login.ILoginPT;
import security.LoginManager;


public class GoogleOAuthLoginAR implements ILoginPT {


    @Override
    public ResponseDN<AuthorizationDN> getAuthorization(LoginManager loginManager) {

        GoogleOAuthSettings gOAuth = (GoogleOAuthSettings) loginManager.getAuthorizationSettings();

        return null;
    }
}
