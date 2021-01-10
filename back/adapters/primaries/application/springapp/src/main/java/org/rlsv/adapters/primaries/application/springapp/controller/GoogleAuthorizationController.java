package org.rlsv.adapters.primaries.application.springapp.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.rlsv.adapters.secondaries.security.oauth2.google.models.GoogleOAuthSettings;
import org.rlsv.adapters.secondaries.security.oauth2.google.techniques.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/authorization")
public class GoogleAuthorizationController {

    GoogleOAuthSettings gOAuth;

    public GoogleAuthorizationController(GoogleOAuthSettings gOAuth) {
        this.gOAuth = gOAuth;
    }

    @GetMapping(value = "/callback")
    public void callback(HttpServletResponse response,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam("code") String code,
                         @RequestParam("scope") String scope) throws Exception {

        long start=System.currentTimeMillis();
        System.out.println(code);
        System.out.println(scope);

        if(Objects.nonNull(error)){
            response.getWriter().println(error);
            return;
        }

        String body = HttpUtils.post(gOAuth.getUrlGetToken(), ImmutableMap.<String, String>builder()
                .put("code", code)
                .put("client_id", gOAuth.getClientId())
                .put("client_secret", gOAuth.getClientSecret())
                .put("redirect_uri",gOAuth.getRedirectUri())
                .put("grant_type", gOAuth.getGrantType()).build());

        System.out.println(body);

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(body);
        JsonNode actualObj = mapper.readTree(parser);

        String jwtToken = actualObj.get("access_token").textValue();

        System.out.println(jwtToken);

        // get some info about the user with the access token
        String userInfo = HttpUtils.get(new StringBuilder(gOAuth.getUrlUserInfo()).append("?access_token=").append(jwtToken).toString());

        // now we could store the email address in session

        // return the json of the user's basic info
        System.out.println(userInfo);
        response.getWriter().println(userInfo);

        System.out.println(String.format("elapsed time : %dms",System.currentTimeMillis()-start));

    }





    @GetMapping(value = "/user_infos")
    public void userInfos() throws Exception {
        System.out.println("userInfos");
    }



    private class TokenEntity {
        String accessToken;
        String expiresIn;
        String scope;
        String tokenType;
    }

}


