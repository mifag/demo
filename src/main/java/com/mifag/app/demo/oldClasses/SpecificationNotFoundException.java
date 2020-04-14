package com.mifag.app.demo.oldClasses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SpecificationNotFoundException extends Exception {

    private static final long serialVersionUID = 8048440822434329276L;

    /**
     * Ошибка, связанная с отсутствием спецификации
     */
    public SpecificationNotFoundException() {
        super("Specification not found.");
    }
}
