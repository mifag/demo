package com.mifag.app.exception;

/**
 * @author <a href='mailto:mifag92@rambler.ru'>mifag</a>
 * @version 03.09.2020
 */
public class SpecificationNotFoundException extends Exception {

    private static final long serialVersionUID = 4848944034518433945L;

    /**
     * Exception, if specification with specific id not found.
     *
     * @param specId - specification id.
     */
    public SpecificationNotFoundException(Long specId) {
        super("Specification with id " + specId + " not found.");
    }
}
