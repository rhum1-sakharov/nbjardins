package org.rlsv.adapters.primaries.application.springapp.config.exception;

import domains.wrapper.ResponseDN;
import exceptions.CleanException;
import exceptions.LoginException;
import exceptions.PersistenceException;
import exceptions.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ports.localization.LocalizeServicePT;

import java.util.Locale;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    LocalizeServicePT localizeServicePT;

    public GlobalControllerExceptionHandler(LocalizeServicePT localizeServicePT) {
        this.localizeServicePT = localizeServicePT;
    }

    @ExceptionHandler(CleanException.class)
    protected ResponseEntity<Object> handleCleanException(CleanException ce, Locale locale) {

        ResponseDN response = new ResponseDN();
        response.addErrorMessage(ce.displayMessage(localizeServicePT, locale));

        HttpStatus status;
        if (ce instanceof TechnicalException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ce instanceof LoginException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ce instanceof PersistenceException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return  new ResponseEntity(response, status);
    }


}
