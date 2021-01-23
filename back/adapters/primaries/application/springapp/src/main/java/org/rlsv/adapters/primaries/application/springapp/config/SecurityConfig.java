package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.security.oauth2.google.GoogleOAuthLoginAR;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ports.login.ILoginPT;

@Configuration
public class SecurityConfig {

    Environment env;

    public SecurityConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public GoogleOAuthSettings googleOAuthSettings() {

        GoogleOAuthSettings googleOAuthSettings = new GoogleOAuthSettings();

        googleOAuthSettings.setClientId(env.getProperty("google.oauth2.client_id"));
        googleOAuthSettings.setClientSecret(env.getProperty("google.oauth2.client_secret"));
        googleOAuthSettings.setRedirectUri(env.getProperty("google.oauth2.redirect_uri"));
        googleOAuthSettings.setScope(env.getProperty("google.oauth2.scope"));
        googleOAuthSettings.setUrlAuthorization(env.getProperty("google.oauth2.url_authorization"));
        googleOAuthSettings.setUrlUserInfo(env.getProperty("google.oauth2.url_user_info"));
        googleOAuthSettings.setUrlGetToken(env.getProperty("google.oauth2.url_get_token"));
        googleOAuthSettings.setGrantType(env.getProperty("google.oauth2.grant_type"));
        googleOAuthSettings.setAccessType(env.getProperty("google.oauth2.access_type"));
        googleOAuthSettings.setResponseType(env.getProperty("google.oauth2.response_type"));
        googleOAuthSettings.setState(env.getProperty("google.oauth2.state"));
        googleOAuthSettings.setRedirectFrontUri(env.getProperty("google.oauth2.redirect_front_uri"));
        googleOAuthSettings.setTokenValidityInMinutes(Integer.parseInt(env.getProperty("google.oauth2.token_validity_in_minutes")));


        return googleOAuthSettings;
    }

    @Bean
    public ILoginPT loginPT() {
        return new GoogleOAuthLoginAR();
    }

}
