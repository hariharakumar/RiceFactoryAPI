package com.projects.ricefactory.errors;

public class NotAuthorizedException extends ApiException {

    public NotAuthorizedException(String message) {
        super(new ErrorDetail<>(ErrorCode.UNAUTHORIZED, message));
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(new ErrorDetail<>(ErrorCode.UNAUTHORIZED, message, cause));
    }
}
