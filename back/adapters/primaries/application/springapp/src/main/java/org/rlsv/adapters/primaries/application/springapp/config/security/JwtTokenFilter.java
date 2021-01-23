package org.rlsv.adapters.primaries.application.springapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import domains.wrapper.ResponseDN;
import exceptions.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ports.localization.LocalizeServicePT;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static localizations.MessageKeys.TOKEN_NOT_VALID;

public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenFilter.class);

    private JwtTokenProvider jwtTokenProvider;
    private LocalizeServicePT localizeService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, LocalizeServicePT localizeService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.localizeService = localizeService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {


        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

        if (Objects.nonNull(token)) {

            try {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (LoginException e) {
                writeTokenError((HttpServletResponse) res);
                return;
            }

        }

        filterChain.doFilter(req, res);

    }

    private void writeTokenError(HttpServletResponse res) throws IOException {

        res.setContentType("application/json; charset=utf-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());

        ResponseDN<Object> objectResponseDN = new ResponseDN<>();
        objectResponseDN.addErrorMessage(localizeService.getMsg(TOKEN_NOT_VALID));

        PrintWriter out = res.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.print(mapper.writeValueAsString(objectResponseDN));
        out.flush();
    }
}
