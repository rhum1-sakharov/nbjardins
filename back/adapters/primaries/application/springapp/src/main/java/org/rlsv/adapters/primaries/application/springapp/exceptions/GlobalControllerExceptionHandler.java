package org.rlsv.adapters.primaries.application.springapp.exceptions;

import domains.wrapper.ResponseDN;
import exceptions.CleanException;
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

        ResponseEntity responseEntity = new ResponseEntity(response, HttpStatus.CONFLICT);

        return responseEntity;
    }


}
