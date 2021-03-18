package org.rlsv.adapters.secondaries.security.oauth2.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import domains.authorizations.AuthorizationDN;
import enums.TYPES_PERSONNE;
import exceptions.LoginException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import org.rlsv.adapters.secondaries.security.oauth2.google.techniques.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.login.ILoginPT;
import security.LoginManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static localizations.MessageKeys.SERVER_ERROR;
import static localizations.MessageKeys.TOKEN_NOT_VALID;


public class GoogleOAuthLoginAR implements ILoginPT {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final Logger LOG = LoggerFactory.getLogger(GoogleOAuthLoginAR.class);

    private final static String ERREUR_GOOGLE_OAUTH = "Authentification google oauth";


    @Override
    public AuthorizationDN getAuthorization(LoginManager loginManager) throws LoginException {

        GoogleOAuthSettings gOAuth = (GoogleOAuthSettings) loginManager.getAuthorizationSettings();

        String bodyGetToken = getBodyAccessToken(gOAuth, loginManager.getTypePersonne());
        String token = getAuthorizationToken(gOAuth.getUrlGetToken(), bodyGetToken);
        String userInfo = getUserInfo(gOAuth.getUrlUserInfo(), token);
        AuthorizationDN authorization = getAuthorizationDN(userInfo);


        LOG.info("userInfo : {}, authorization : {}", userInfo, authorization.toString());

        return authorization;
    }

    @Override
    public String redirectToThirdServerAuthorization(LoginManager loginManager) throws LoginException {

        GoogleOAuthSettings gOAuth = (GoogleOAuthSettings) loginManager.getAuthorizationSettings();

        StringBuilder urlAuthorization = new StringBuilder();

        // 1 Redirect to Google's OAuth 2.0 server
        try {
            urlAuthorization.append(gOAuth.getUrlAuthorization())
                    .append("?scope=").append(URLEncoder.encode(gOAuth.getScope(), StandardCharsets.UTF_8.toString()))
                    .append("&access_type=").append(gOAuth.getAccessType())
                    .append("&response_type=").append(gOAuth.getResponseType())
                    .append("&state=").append(gOAuth.getState())
                    .append("&redirect_uri=").append(URLEncoder.encode(gOAuth.getRedirectUri() + "?typePersonne=" + loginManager.getTypePersonne(), StandardCharsets.UTF_8.toString()))
                    .append("&client_id=").append(gOAuth.getClientId());

            return urlAuthorization.toString();

        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            throw new LoginException(String.format("%s : Impossible d'encoder l'url %s", ERREUR_GOOGLE_OAUTH, urlAuthorization.toString()), e, SERVER_ERROR, new String[]{e.getMessage()});
        }
    }

    @Override
    public String generateToken(LoginManager loginManager, AuthorizationDN authorization, List<String> roles) {

        GoogleOAuthSettings gOAuth = (GoogleOAuthSettings) loginManager.getAuthorizationSettings();

        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime expirationDate = dateStart.plusMinutes(gOAuth.getTokenValidityInMinutes());

        String jwtToken = Jwts.builder()
                .setSubject(authorization.getEmail())
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .claim("roles", roles)
                .claim("nom", authorization.getNom())
                .claim("prenom", authorization.getPrenom())
                .claim("logo",authorization.getPicture())
                .signWith(key).compact();

        return jwtToken;
    }

    @Override
    public String getUserName(String token) throws LoginException {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (JwtException ex) {
            LOG.error(ex.getMessage());
            throw new LoginException("L'autorisation n'est pas ou plus valide pour le token : " + token, ex, TOKEN_NOT_VALID);
        }


    }

    private String getUserInfo(String urlUserInfo, String token) throws LoginException {
        String userInfo;
        try {
            userInfo = HttpUtils.get(new StringBuilder(urlUserInfo).append("?access_token=").append(token).toString());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new LoginException(String.format("Impossible d'executer la requete %s avec le token %s", urlUserInfo, token), e, SERVER_ERROR, new String[]{e.getMessage()});
        }

        return userInfo;

    }

    /**
     * Exchange authorization code for refresh and access tokens
     *
     * @param gOAuth
     * @return
     * @throws LoginException
     */
    private String getBodyAccessToken(GoogleOAuthSettings gOAuth, TYPES_PERSONNE types_personne) throws LoginException {

        String bodyGetToken;

        try {

            bodyGetToken = HttpUtils.post(gOAuth.getUrlGetToken(), ImmutableMap.<String, String>builder()
                    .put("code", gOAuth.getCode())
                    .put("client_id", gOAuth.getClientId())
                    .put("client_secret", gOAuth.getClientSecret())
                    .put("redirect_uri", gOAuth.getRedirectUri() + "?typePersonne=" + types_personne)
                    .put("grant_type", gOAuth.getGrantType()).build());


        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new LoginException(String.format("Impossible d'executer la requete POST %s", gOAuth.getUrlGetToken()), e, SERVER_ERROR, new String[]{e.getMessage()});
        }

        return bodyGetToken;

    }

    private String getAuthorizationToken(String urlGetToken, String bodyGetToken) throws LoginException {

        String token;
        try {
            JsonNode actualObj = HttpUtils.jsonParser(bodyGetToken);
            token = actualObj.get("access_token").textValue();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new LoginException(String.format("Impossible de parser le contenu de la reponse POST %s", urlGetToken), e, SERVER_ERROR, new String[]{e.getMessage()});
        }

        return token;
    }

    private AuthorizationDN getAuthorizationDN(String userInfo) throws LoginException {

        AuthorizationDN authorization = new AuthorizationDN();
        try {
            JsonNode actualObj = HttpUtils.jsonParser(userInfo);

            String prenom = actualObj.get("given_name").textValue();
            String nom = actualObj.get("family_name").textValue();
            String email = actualObj.get("email").textValue();
            String photo = actualObj.get("picture").textValue();

            authorization.setEmail(email);
            authorization.setNom(nom);
            authorization.setPrenom(prenom);
            authorization.setPicture(photo);

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new LoginException(String.format("Impossible de parser %s", userInfo), e, SERVER_ERROR, new String[]{e.getMessage()});
        }

        return authorization;
    }
}
