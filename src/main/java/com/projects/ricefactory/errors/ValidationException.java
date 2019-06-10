package com.projects.ricefactory.errors;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Thrown when fields fail validation, or the state of some request is invalid
 */
public class ValidationException extends ApiException {

    private static <T> List<ErrorDetail> fromConstraintViolations(Set<ConstraintViolation<T>> violations) {
        List<ErrorDetail> errorDetailList = new ArrayList<>(violations.size());
        for(ConstraintViolation<T> constraintViolation : violations) {
            String field = constraintViolation.getPropertyPath().toString();
            ErrorCode code = ErrorCode.BAD_VALUE;
            String message = constraintViolation.getMessage();
            ErrorDetail ed = new ErrorDetail<>(field, code, message, null);
            errorDetailList.add(ed);
        }

        return errorDetailList;
    }

    public <T> ValidationException(Set<ConstraintViolation<T>> violations) {
        super(fromConstraintViolations(violations));
    }

    /**
     * use when returning a single error with message
     */
    public ValidationException(String message) {
        super(new ErrorDetail<>(ErrorCode.BAD_VALUE, message));
    }

    /**
     * use when returning a single error with message
     */
    public ValidationException(String message, Throwable cause) {
        super(new ErrorDetail<>(ErrorCode.BAD_VALUE, message), cause);
    }

    /**
     * use when returning a single error with code, and message
     */
    public ValidationException(ErrorCode code, String message) {
        super(new ErrorDetail<>(code, message));
    }

    /**
     * use when returning a single error with code, and message
     */
    public ValidationException(ErrorCode code, String message, Throwable cause) {
        super(new ErrorDetail<>(code, message), cause);
    }

    /**
     * use when returning a single error with code, message, and details payload
     */
    public <T> ValidationException(ErrorCode code, String message, T details) {
        super(new ErrorDetail<>(code, message, details));
    }

    /**
     * use when returning a single error with code, message, and details payload
     */
    public <T> ValidationException(ErrorCode code, String message, T details, Throwable cause) {
        super(new ErrorDetail<>(code, message, details), cause);
    }

    /**
     * use when returning a single error with field, code, and message
     */
    public ValidationException(String field, ErrorCode code, String message) {
        super(new ErrorDetail<>(field, code, message, null));
    }

    /**
     * use when returning a single error with field, code, message, and details payload
     */
    public <T> ValidationException(String field, ErrorCode code, String message, T details) {
        super(new ErrorDetail<>(field, code, message, details));
    }

    /**
     * use when returning a single error with field, code, message, and details payload
     */
    public <T> ValidationException(String field, ErrorCode code, String message, T details, Throwable cause) {
        super(new ErrorDetail<>(field, code, message, details), cause);
    }

    /**
     * use when validating a form w/ multiple field errors
     * @param errorDetailList list of field errors
     */
    public ValidationException(List<ErrorDetail> errorDetailList) {
        super(errorDetailList);
    }

    /**
     * use when validating a form w/ multiple field errors. allows setting exception message
     * @param errorDetailList list of field errors
     */
    public ValidationException(List<ErrorDetail> errorDetailList, String message) {
        super(errorDetailList, message);
    }

    /**
     * use when validating a form w/ multiple field errors. allows setting exception message and cause exception
     * @param errorDetailList list of field errors
     */
    public ValidationException(List<ErrorDetail> errorDetailList, String message, Throwable cause) {
        super(errorDetailList, message, cause);
    }
}

