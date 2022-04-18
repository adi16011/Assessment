package com.Bootcamp.project.ExceptionHandlers;

import java.util.Date;
import java.util.Set;

public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private Set<String> details;

    public ExceptionResponse(Date timestamp, String message, Set<String> details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Set<String> getDetails() {
        return details;
    }
}