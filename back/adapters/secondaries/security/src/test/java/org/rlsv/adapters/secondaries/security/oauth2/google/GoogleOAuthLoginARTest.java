package org.rlsv.adapters.secondaries.security.oauth2.google;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.security.Key;

public class GoogleOAuthLoginARTest {

    @Test
    public void generateToken() {


        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

        Assertions.assertThat(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe"));

        jws = "toto";

        try {
            //NOTE: Ensure you call the parseClaimsJws method (since there are many similar methods available). You will get an UnsupportedJwtException if you parse your JWT with wrong method.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
        } catch (JwtException e) {

            System.out.println("jwt not valid : "+e.getMessage());
        }
    }
}