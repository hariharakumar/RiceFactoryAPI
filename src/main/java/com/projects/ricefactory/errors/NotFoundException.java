package com.projects.ricefactory.errors;

import static com.projects.ricefactory.errors.ErrorCode.NOT_FOUND;
import static java.util.Collections.singletonMap;

/**
 * Thrown when the thing being requested cannot be found.
 */
public class NotFoundException extends ApiException {
    public NotFoundException(String msg, String entityId) {
        this(msg, "id", entityId);
    }

    public NotFoundException(String msg, String idField, String entityId) {
        super(new ErrorDetail<>(null, NOT_FOUND, msg,singletonMap(idField, entityId)));
    }

    public NotFoundException(String msg, String entityId, Throwable cause) {
        this(msg, "id", entityId, cause);
    }

    public NotFoundException(String msg, String idField, String entityId, Throwable cause) {
        super(new ErrorDetail<>(null, NOT_FOUND, msg,singletonMap(idField, entityId)), cause);
    }
}
