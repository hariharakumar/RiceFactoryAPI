package com.projects.ricefactory.dto;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Response error.  Standardized error response with the field name, error code and error message.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseError {
    public static final String REQUIRED_CODE  = "required";
    public static final String NUMERIC_CODE   = "numeric";
    public static final String BOOLEAN_CODE   = "boolean";
    public static final String RANGE_CODE     = "range";
    public static final String UNIQUE_CODE    = "unique";
    public static final String NOT_FOUND_CODE = "notFound";
    public static final String SIZE_CODE      = "size";

    private String field;
    private String code;
    private String min;
    private String max;
    private String message;

    public ResponseError() {
    }

    public ResponseError(String message) {
        this.message = message;
    }

    public ResponseError(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    public ResponseError(String field, String code, String min, String max, String message) {
        this.field = field;
        this.code = code;
        this.min = min;
        this.max = max;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
