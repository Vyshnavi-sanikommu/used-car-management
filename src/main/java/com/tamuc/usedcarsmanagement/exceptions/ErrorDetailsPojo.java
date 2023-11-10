package com.tamuc.usedcarsmanagement.exceptions;

import java.util.Date;

public class ErrorDetailsPojo {

    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsPojo(Date timestamp, String message, String details) {
        super();
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

    public String getDetails() {
        return details;
    }
}
