package org.rlsv.adapters.primaries.application.springapp.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {


    final String AUTH_URL_ACCESS_TOKEN = "https://oauth2.googleapis.com/token";
    final String AUTH_URL_ACCESS_EMAIL = "https://openidconnect.googleapis.com/v1/userinfo";
    final String REDIRECT_URI_USER_INFO = "http://localhost:8080/api/authorization/user_infos";
    final String OAUTH_GOOGLE_ID = "342992225722-f12jtc44h4gvsdn7kepj4nuudn91lfhj.apps.googleusercontent.com";
    final String OAUTH_GOOGLE_SECRET = "_1R5cJ2dpBF8cxt4wIC0VMUZ";
    final String OAUTH_GOOGLE_REDIRECT_URI = "http://localhost:4200/api/authorization/callback";


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

        String body = post(AUTH_URL_ACCESS_TOKEN, ImmutableMap.<String, String>builder()
                .put("code", code)
                .put("client_id", OAUTH_GOOGLE_ID)
                .put("client_secret", OAUTH_GOOGLE_SECRET)
                .put("redirect_uri",OAUTH_GOOGLE_REDIRECT_URI)
                .put("grant_type", "authorization_code").build());

        System.out.println(body);

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(body);
        JsonNode actualObj = mapper.readTree(parser);

        String jwtToken = actualObj.get("access_token").textValue();

        System.out.println(jwtToken);

        // get some info about the user with the access token
        String userInfo = get(new StringBuilder(AUTH_URL_ACCESS_EMAIL).append("?access_token=").append(jwtToken).toString());

        // now we could store the email address in session

        // return the json of the user's basic info
        System.out.println(userInfo);
        response.getWriter().println(userInfo);

        System.out.println(String.format("elapsed time : %dms",System.currentTimeMillis()-start));

    }

    // makes a POST request to url with form parameters and returns body as a string
    public String post(String url, Map<String, String> formParameters) throws ClientProtocolException, IOException {
        HttpPost request = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for (String key : formParameters.keySet()) {
            nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
        }

        request.setEntity(new UrlEncodedFormEntity(nvps));

        return execute(request);
    }

    // makes request and checks response code for 200
    private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
        }

        return body;
    }


    @GetMapping(value = "/user_infos")
    public void userInfos() throws Exception {
        System.out.println("userInfos");
    }

    // makes a GET request to url and returns body as a string
    public String get(String url) throws ClientProtocolException, IOException {
        return execute(new HttpGet(url));
    }

    private class TokenEntity {
        String accessToken;
        String expiresIn;
        String scope;
        String tokenType;
    }

}


