package com.Bootcamp.project.ExceptionHandlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        Set<String> errors = new HashSet<>(errorList);
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ex.getLocalizedMessage(), errors);

        return handleExceptionInternal(ex, errorDetails, headers,HttpStatus.BAD_REQUEST, request);
    }

//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public Set<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//
//
//        Set<ExceptionResponse> errorMap = new HashSet<>();
//
//
//
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
////            errorMap.put(error.getField(),error.getDefaultMessage());
//            Set<String> errors = new HashSet<>();
//            errors.add(error.getDefaultMessage());
//
//            errorMap.add(new ExceptionResponse(new Date(),error.getField(),errors));
//
//
//        });
//
//        return errorMap;
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ResourceAlreadyExistException.class)
    public ExceptionResponse handleResourceAlreadyExistException(ResourceAlreadyExistException ex){

        Set<String> errors = new HashSet<>();
        errors.add(ex.getLocalizedMessage());


        ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(), errors);
        return response;

    }


    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse handleAllException(Exception ex){

        Set<String> errors = new HashSet<>();
        errors.add(ex.getLocalizedMessage());


        ExceptionResponse ee = new ExceptionResponse(new Date(), ex.getMessage(), errors);

        return ee;


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public final ResponseEntity<Object> customerAlreadyExist(UserExistException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        Set<String> errors = new HashSet<>(list);

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Customer already Exist", errors);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchingException.class)
    public final ResponseEntity<Object> passwordMismatchException(PasswordNotMatchingException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        Set<String> errors = new HashSet<>(list);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Password Mismatch",errors);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}