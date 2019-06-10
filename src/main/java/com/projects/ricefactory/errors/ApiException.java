package com.projects.ricefactory.errors;

import java.util.List;

/**
 * <p>This class sits at the top of our exception hierarchy allowing for simplified error handling and
 * standard error payloads. This class is designed for subclassing and should not be instantiated
 * directly, except for rare cases when {@link #ApiException(String, Throwable)} makes sense.</p>
 *
 * <p>Multiple telescoping constructors have been provided for convenience,
 * they fall into a few categories of use:
 * <ul><li>Single error:  ApiException(ErrorDetail) used for most error responses.  subclasses will
 *     generate the ErrorDetail, picking the Code, etc...</li>
 * <li>Multi error: ApiException(List&lt;ErrorDetail&gt;) used for passing multiple errors, usually
 *     for form validation</li>
 * <li>With cause: ApiException(... [, exceptionMessage] [, cause]) allows supplying internal details to
 *     the exception.  These details will be captured in the server error logs, but will <b>NOT</b> be sent
 *     to the client.</li>
 * </ul>
 * </p>
 */
public class ApiException extends RuntimeException {
    private final ErrorDetails errorDetails;

    <T> ApiException(ErrorDetail<T> errorDetail) {
        super();
        errorDetails = new ErrorDetails(errorDetail);
    }

    <T> ApiException(ErrorDetail<T> errorDetail, String exceptionMessage) {
        super(exceptionMessage);
        errorDetails = new ErrorDetails(errorDetail);
    }

    <T> ApiException(ErrorDetail<T> errorDetail, Throwable cause) {
        super(cause);
        errorDetails = new ErrorDetails(errorDetail);
    }

    <T> ApiException(ErrorDetail<T> errorDetail, String exceptionMessage, Throwable cause) {
        super(exceptionMessage, cause);
        errorDetails = new ErrorDetails(errorDetail);
    }

    ApiException(List<ErrorDetail> errorDetailList) {
        super();
        errorDetails = new ErrorDetails(errorDetailList);
    }

    ApiException(List<ErrorDetail> errorDetailList, String exceptionMessage) {
        super(exceptionMessage);
        errorDetails = new ErrorDetails(errorDetailList);
    }

    ApiException(List<ErrorDetail> errorDetailList, Throwable cause) {
        super(cause);
        errorDetails = new ErrorDetails(errorDetailList);
    }

    ApiException(List<ErrorDetail> errorDetailList, String exceptionMessage, Throwable cause) {
        super(exceptionMessage, cause);
        errorDetails = new ErrorDetails(errorDetailList);
    }

    /**
     * use this when you're not sure what to pass... turns any exception into a runtime exception
     * this shouldn't be commonly used
     */
    public ApiException(String exceptionMessage, Throwable throwable) {
        super(exceptionMessage, throwable);
        errorDetails = new ErrorDetails(
                null,
                ErrorCode.UNKOWN_ERROR,
                "An unknown error occurred",
                null
        );
    }

    ErrorDetails getErrorDetails() {
        return errorDetails;
    }
}
