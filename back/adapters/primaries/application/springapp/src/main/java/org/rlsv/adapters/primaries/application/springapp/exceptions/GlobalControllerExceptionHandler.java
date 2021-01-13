package org.rlsv.adapters.primaries.application.springapp.exceptions;

import domains.wrapper.ResponseDN;
import exceptions.CleanException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CleanException.class)
    protected ResponseEntity<Object> handleCleanException(CleanException ce) {

        ResponseDN response = new ResponseDN();
        response.addErrorMessage(ce.getMessage());

        ResponseEntity responseEntity = new ResponseEntity(response,HttpStatus.CONFLICT);

        return responseEntity;
    }



}
