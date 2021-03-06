package org.rlsv.adapters.primaries.application.springapp.controller;

import domains.authorizations.AuthorizationDN;
import enums.TYPES_PERSONNE;
import exceptions.CleanException;
import exceptions.LoginException;
import exceptions.TechnicalException;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ports.login.ILoginPT;
import security.LoginManager;
import usecases.authorization.GetAuthorizationUE;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import static localizations.MessageKeys.SERVER_ERROR;

@RestController
@RequestMapping("/authorization")
public class GoogleAuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthorizationController.class);

    GoogleOAuthSettings gOAuth;
    ILoginPT googleLogin;
    GetAuthorizationUE getAuthorizationUE;

    public GoogleAuthorizationController(GoogleOAuthSettings gOAuth, ILoginPT loginPT, GetAuthorizationUE getAuthorizationUE) {
        this.gOAuth = gOAuth;
        this.googleLogin = loginPT;
        this.getAuthorizationUE = getAuthorizationUE;
    }

    @GetMapping(value = "/initiate-google-oauth")
    public void initiateGoogleOAuth(HttpServletResponse response, @RequestParam(value = "typePersonne", required = false) TYPES_PERSONNE typePersonne) throws CleanException {

        LoginManager loginManager = new LoginManager(typePersonne, gOAuth);

        try {

            String redirectUrl = this.googleLogin.redirectToThirdServerAuthorization(loginManager);
            response.sendRedirect(redirectUrl);


        } catch (LoginException e) {
            try {
                response.getWriter().println(e.getMessage());
            } catch (IOException e1) {
                throw new TechnicalException(e1.getMessage(), e, SERVER_ERROR, new String[]{e1.getMessage()});
            }
            return;
        } catch (IOException e) {
            throw new TechnicalException(e.getMessage(), e, SERVER_ERROR, new String[]{e.getMessage()});
        }
    }

    @GetMapping(value = "/callback")
    public void callback(HttpServletResponse response,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "typePersonne", required = false) TYPES_PERSONNE typePersonne,
                         @RequestParam("code") String code,
                         Locale locale
    ) throws Exception {

        if (Objects.nonNull(error)) {
            response.getWriter().println(error);
            return;
        }

        long start = System.currentTimeMillis();

        // le code temporaire permettant d'obtenir un access token
        gOAuth.setCode(code);
        LoginManager loginManager = new LoginManager(typePersonne, gOAuth);

        // creer le compte s'il n'existe pas et recuperer l'authoriation
        AuthorizationDN authorization = getAuthorizationUE.execute( null, loginManager);


        // rediriger sur l'application front en  fournissant le jwt en parametre
        StringBuilder redirectFrontUri = new StringBuilder();
        redirectFrontUri.append(gOAuth.getRedirectFrontUri())
                .append("?token=").append(authorization.getToken());
        response.sendRedirect(redirectFrontUri.toString());


        LOG.info("jwtToken : {}", authorization.getToken());
        LOG.info(String.format("callback elapsed time : %dms", System.currentTimeMillis() - start));

    }


}


