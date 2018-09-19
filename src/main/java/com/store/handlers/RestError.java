package com.store.handlers;

import com.store.config.StaticUtils;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class RestError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public RestError(HttpStatus status, String message, List<String> errors) {
        super();
        this.setStatus(status);
        this.setMessage(message);
        this.setErrors(errors);
    }

    public RestError(HttpStatus status, String message, String error) {
        super();
        this.setStatus(status);
        this.setMessage(message);
        this.setErrors(Arrays.asList(error));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {

        boolean developmentEnv = StaticUtils.getProperty();
        if(developmentEnv)
            this.errors = errors;
    }
}
