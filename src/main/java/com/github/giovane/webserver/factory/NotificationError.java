package com.github.giovane.webserver.factory;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public final class NotificationError {

    private HttpStatus statusCode;

    private final Map<String, String> errors;

    private boolean hasErrors;

    public NotificationError() {
        this.statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        this.errors = new HashMap<>();
        this.hasErrors = false;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        this.hasErrors = true;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(String key, String value) {
        this.errors.put(key, value);
        this.hasErrors = true;
    }

    public boolean hasErrors() {
        return this.hasErrors;
    }

    public void captureErrors(BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            this.setErrors(fieldName, errorMessage);
        });
        this.setStatusCode(HttpStatus.BAD_REQUEST);

    }

}
