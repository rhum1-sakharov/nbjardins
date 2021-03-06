package org.rlsv.adapters.primaries.application.springapp.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ports.localization.LocalizeServicePT;

@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    enum ROLES{
        ARTISAN("ARTISAN"),
        CLIENT("CLIENT");

        String value;

        ROLES(String value) {
            this.value = value;
        }


    }

    JwtTokenProvider jwtTokenProvider;
    LocalizeServicePT localizeService;

    public JwtSecurityConfig(JwtTokenProvider jwtTokenProvider, LocalizeServicePT localizeService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.localizeService = localizeService;
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/graphql").hasRole(ROLES.ARTISAN.value)
                .antMatchers( "/authorization/**").permitAll()
                .antMatchers( "/devis/**").permitAll()
                .antMatchers( "/artisans/**").hasRole(ROLES.ARTISAN.value)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider,localizeService));
        //@formatter:on
    }
}
