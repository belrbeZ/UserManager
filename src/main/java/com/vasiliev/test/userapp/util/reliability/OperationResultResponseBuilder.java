package com.vasiliev.test.userapp.util.reliability;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

import static com.vasiliev.test.userapp.util.reflection.Reflections.*;

/**
 * The type Operation result response builder.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
public class OperationResultResponseBuilder {

    private Object content;
    private String description;
    private OperationResultStatus status;
    private Function<OperationResultStatus, HttpStatus> statusMapper;
    private Class<?> type;
    private Long contentLength;

    /**
     * Content operation result response builder.
     *
     * @param content the content
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder content(final Object content) {
        this.content = content;
        return this;
    }

    /**
     * Description operation result response builder.
     *
     * @param description the description
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder description(final String description) {
        this.description = description;
        return this;
    }

    /**
     * Status operation result response builder.
     *
     * @param status the status
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder status(final OperationResultStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Status mapper operation result response builder.
     *
     * @param statusMapper the status mapper
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder statusMapper(final Function<OperationResultStatus, HttpStatus> statusMapper) {
        this.statusMapper = statusMapper;
        return this;
    }

    /**
     * Type operation result response builder.
     *
     * @param type the type
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder type(final Class<?> type) {
        this.type = type;
        return this;
    }

    /**
     * Content length operation result response builder.
     *
     * @param contentLength the content length
     * @return the operation result response builder
     */
    public OperationResultResponseBuilder contentLength(final Long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    /**
     * Response response entity.
     *
     * @param <T> the type parameter
     * @return the response entity
     */
    public <T> ResponseEntity<T> response() {
        final T body = operationResult();
        final HttpStatus httpStatus = statusMapper.apply(status);
        return new ResponseEntity<T>(body, httpStatus);
    }

    private <T> T operationResult() {
        final T operationResult = cast(instantiate(type));
        if (content != null) {
            invoke("setContent", operationResult, content);
        }
        invoke("setDescription", operationResult, operationResultDescription());
        invoke("setStatusCode", operationResult, status.getCode());
        if (contentLength != null) {
            invoke("setContentLength", operationResult, contentLength);
        }
        return operationResult;
    }

    private String operationResultDescription() {
        return description != null ? description : status.getDefaultDescription();
    }
}
