package com.vasiliev.test.userapp.util.reliability;

/**
 * The type Operation exception.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
public class OperationException extends RuntimeException {

    private final String description;
    private final OperationResultStatus status;

    /**
     * Instantiates a new Operation exception.
     *
     * @param status the status
     */
    public OperationException(final OperationResultStatus status) {
        this(status, null);
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param status      the status
     * @param description the description
     */
    public OperationException(final OperationResultStatus status, final String description) {
        this(status, description, null);
    }

    /**
     * Instantiates a new Operation exception.
     *
     * @param status      the status
     * @param description the description
     * @param cause       the cause
     */
    public OperationException(final OperationResultStatus status, final String description, final Throwable cause) {
        super(cause);
        this.status = status;
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public OperationResultStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", status.getCode(), description != null ? description : status.getDefaultDescription());
    }
}
