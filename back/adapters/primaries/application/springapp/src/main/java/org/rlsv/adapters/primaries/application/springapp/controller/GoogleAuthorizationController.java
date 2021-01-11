package org.rlsv.adapters.primaries.application.springapp.controller;

import enums.TYPES_PERSONNE;
import exceptions.LoginException;
import org.rlsv.adapters.secondaries.security.oauth2.google.GoogleOAuthLoginAR;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ports.login.ILoginPT;
import security.LoginManager;
import usecases.login.LoginUE;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/authorization")
public class GoogleAuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleOAuthLoginAR.class);

    GoogleOAuthSettings gOAuth;
    ILoginPT googleLogin;
    LoginUE loginUE;

    public GoogleAuthorizationController(GoogleOAuthSettings gOAuth, ILoginPT loginPT,LoginUE loginUE) {
        this.gOAuth = gOAuth;
        this.googleLogin = loginPT;
        this.loginUE = loginUE;
    }

    @GetMapping(value = "/initiate-google-oauth")
    public void initiateGoogleOAuth(HttpServletResponse response, @RequestParam(value = "typePersonne", required = false) TYPES_PERSONNE typePersonne) throws IOException {

        LoginManager loginManager = new LoginManager(typePersonne, gOAuth);

        try {
            String redirectUrl = this.googleLogin.redirectToThirdServerAuthorization(loginManager);
            response.sendRedirect(redirectUrl);

        } catch (LoginException e) {
            response.getWriter().println(e.getMessage());
            return;
        }
    }

    @GetMapping(value = "/callback")
    public void callback(HttpServletResponse response,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "typePersonne", required = false) TYPES_PERSONNE typePersonne,
                         @RequestParam("code") String code,
                         Locale locale
                         ) throws Exception {

        if(Objects.nonNull(error)){
            response.getWriter().println(error);
            return;
        }

        long start = System.currentTimeMillis();

        // le code temporaire permettant d'obtenir un access token
        gOAuth.setCode(code);
        LoginManager loginManager = new LoginManager(typePersonne,gOAuth);

        loginUE.execute(null,loginManager);

       LOG.info(String.format("callback elapsed time : %dms", System.currentTimeMillis() - start));

    }


}


