package com.xipsoft.hotelrestapi.controller.error;

import java.util.Date;

public class ApiError {
    private Date timestamp;
    private String message;
    private String details;
    private String path;

    public ApiError(Date timestamp, String message, String details, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getPath() {
        return path;
    }
}
