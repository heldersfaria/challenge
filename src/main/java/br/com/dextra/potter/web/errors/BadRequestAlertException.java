package br.com.dextra.potter.web.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BadRequestAlertException extends RuntimeException {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String detailedMessage;
    private List<ApiSubError> errors;

    public BadRequestAlertException() {
        status = HttpStatus.BAD_REQUEST;
        timestamp = LocalDateTime.now();
        errors = new ArrayList<>();
    }

    public BadRequestAlertException(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.detailedMessage = ex.getLocalizedMessage();
    }

    public BadRequestAlertException(String message) {
        this();
        this.message = message;
    }

    public BadRequestAlertException(String message, Throwable ex) {
        this();
        this.message = message;
        this.detailedMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    public List<ApiSubError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiSubError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BadRequestAlertException{");
        sb.append("status=").append(status);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", message='").append(message).append('\'');
        sb.append(", detailedMessage='").append(detailedMessage).append('\'');
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
