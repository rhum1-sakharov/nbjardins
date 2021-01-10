package org.rlsv.adapters.secondaries.security.oauth2.google.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleOAuthSettings {

    private String clientId;
    private String clientSecret;
    private String scope;
    private String redirectUri;
    private String urlAuthorization;
    private String urlGetToken;
    private String urlUserInfo;
    private String grantType;
    private String accessType;
    private String responseType;
    private String state;
    private String code;

}
