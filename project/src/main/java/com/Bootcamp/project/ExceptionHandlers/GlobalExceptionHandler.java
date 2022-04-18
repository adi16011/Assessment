package com.Bootcamp.project.ExceptionHandlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Controller
//@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse handleAllException(Exception e){

        Set<String> errors = new HashSet<>();
        errors.add(e.getLocalizedMessage());



        ExceptionResponse ex = new ExceptionResponse(new Date(), e.getMessage(), errors);









        return ex;

    }
}
