package com.xipsoft.hotelrestapi.controller.error;

import java.util.Date;
import java.util.Map;

public class ValidationError {
    private Date timestamp;
    private String message;
    private Map<String,String> details;

    public ValidationError(Date timestamp, String message, Map<String, String> details) {
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

    public Map<String, String> getDetails() {
        return details;
    }
}
