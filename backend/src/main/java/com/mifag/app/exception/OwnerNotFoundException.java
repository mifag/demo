package com.mifag.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends Exception {

    private static final long serialVersionUID = 9182137595163235718L;

    /**
     * Exception, if owner with specific id not found.
     *
     * @param ownerId .
     */
    public OwnerNotFoundException(Long ownerId) {
        super("Owner with id " + ownerId + " not found.");
    }
}
