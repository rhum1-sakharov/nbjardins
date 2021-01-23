package org.rlsv.adapters.primaries.application.springapp.config.security;

import exceptions.LoginException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ports.login.ILoginPT;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

    ILoginPT loginService;
    UserDetailsService userDetailsService;

    public JwtTokenProvider(ILoginPT loginService, UserDetailsService userDetailsService) {
        this.loginService = loginService;
        this.userDetailsService = userDetailsService;
    }

    public Authentication getAuthentication(String token) throws LoginException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginService.getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
