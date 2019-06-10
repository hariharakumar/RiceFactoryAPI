package com.projects.ricefactory.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

class ErrorDetails {

    private final List<ErrorDetail> errorDetailList;

    <T> ErrorDetails(String field, ErrorCode code, String message, T details) {
        this.errorDetailList = Collections.singletonList(new ErrorDetail<>(field, code, message, details));
    }

    <T> ErrorDetails(ErrorDetail<T> errorDetail) {
        this.errorDetailList = Collections.singletonList(errorDetail);
    }

    ErrorDetails(List<ErrorDetail> errorDetailList) {
        this.errorDetailList = errorDetailList;
    }

    @JsonProperty("errors")
    List<ErrorDetail> getErrorDetailList() {
        return errorDetailList;
    }
}
