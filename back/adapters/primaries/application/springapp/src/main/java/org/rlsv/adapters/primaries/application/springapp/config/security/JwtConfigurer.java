package org.rlsv.adapters.primaries.application.springapp.config.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ports.localization.LocalizeServicePT;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private LocalizeServicePT localizeService;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, LocalizeServicePT localizeService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.localizeService = localizeService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider,localizeService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
