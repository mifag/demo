package com.mifag.app.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MidiKeyboardNotFoundException extends Exception {

    private static final long serialVersionUID = -4505069750864670040L;

    public MidiKeyboardNotFoundException(String model) {
        super("MidiKeyboard with model " + model + " not found.");
    }

    public MidiKeyboardNotFoundException(Long id) {
        super("MidiKeyboard with id " + id + " not found.");
    }
}
