package com.Bootcamp.project.ExceptionHandlers;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }

}