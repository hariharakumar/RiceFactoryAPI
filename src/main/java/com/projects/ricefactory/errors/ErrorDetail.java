package com.projects.ricefactory.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorDetail<T> {
    private final String field;
    private final ErrorCode code;
    private final String message;
    private final T details;

    public ErrorDetail(ErrorCode code, String message) {
        this(null, code, message, null);
    }

    public ErrorDetail(String field, ErrorCode code, String message) {
        this(field, code, message, null);
    }

    public ErrorDetail(ErrorCode code, String message, T details) {
        this(null, code, message, details);
    }

    public ErrorDetail(String field, ErrorCode code, String message, T details) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getField() {
        return field;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getDetails() {
        return details;
    }
}
