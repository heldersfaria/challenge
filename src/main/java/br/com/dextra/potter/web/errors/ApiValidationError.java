package br.com.dextra.potter.web.errors;

import java.util.Objects;

public class ApiValidationError implements ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;


    public ApiValidationError() {
    }

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiValidationError)) return false;
        ApiValidationError that = (ApiValidationError) o;
        return Objects.equals(getObject(), that.getObject()) && Objects.equals(getField(), that.getField()) && Objects.equals(getRejectedValue(), that.getRejectedValue()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObject(), getField(), getRejectedValue(), getMessage());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApiValidationError{");
        sb.append("object='").append(object).append('\'');
        sb.append(", field='").append(field).append('\'');
        sb.append(", rejectedValue=").append(rejectedValue);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
