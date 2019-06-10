package com.projects.ricefactory.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Error response body.  Standardized error response with multiple errors.
 */
public class ErrorResponseBody {
    private List<ResponseError> errors = new ArrayList<>();

    public ErrorResponseBody() {
    }

    public ErrorResponseBody(List<ResponseError> errors) {
        this.errors = errors;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }
}
