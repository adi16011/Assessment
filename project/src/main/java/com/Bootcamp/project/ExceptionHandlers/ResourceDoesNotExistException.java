package com.Bootcamp.project.ExceptionHandlers;

public class ResourceDoesNotExistException extends RuntimeException{
    public ResourceDoesNotExistException(String message) {
        super(message);
    }
}