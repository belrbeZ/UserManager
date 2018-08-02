package com.vasiliev.test.userapp.util.reliability;

public class OperationException extends RuntimeException {

    private final String description;
    private final OperationResultStatus status;

    public OperationException(final OperationResultStatus status) {
        this(status, null);
    }

    public OperationException(final OperationResultStatus status, final String description) {
        this(status, description, null);
    }

    public OperationException(final OperationResultStatus status, final String description, final Throwable cause) {
        super(cause);
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public OperationResultStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", status.getCode(), description != null ? description : status.getDefaultDescription());
    }
}
