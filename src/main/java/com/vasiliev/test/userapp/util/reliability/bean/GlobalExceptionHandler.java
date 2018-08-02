package com.vasiliev.test.userapp.util.reliability.bean;

import com.vasiliev.test.userapp.model.OperationResult;
import com.vasiliev.test.userapp.util.reliability.OperationException;
import com.vasiliev.test.userapp.util.reliability.OperationResultResponseBuilder;
import com.vasiliev.test.userapp.util.reliability.OperationResultStatusMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import static com.vasiliev.test.userapp.util.reliability.OperationResultStatus.FAILURE_INTERNAL_UNKNOWN;
import static com.vasiliev.test.userapp.util.reliability.OperationResultStatus.FAILURE_VALIDATION;


/**
 * The type Global exception handler.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private OperationResultStatusMapper operationResultStatusMapper;

    /**
     * Handle type mismatch exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(final TypeMismatchException exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(FAILURE_VALIDATION)
                .description(exception.getMessage())
                .response();
    }

    /**
     * Handle invalid argument exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgumentException(final MethodArgumentNotValidException exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(FAILURE_VALIDATION)
                .description(exception.getMessage())
                .response();
    }

    /**
     * Handle message not readable exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadableException(final HttpMessageNotReadableException exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(FAILURE_VALIDATION)
                .description(exception.getMessage())
                .response();
    }

    /**
     * Handle method not supported exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(FAILURE_VALIDATION)
                .description(exception.getMessage())
                .response();
    }

    /**
     * Handle operation exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(OperationException.class)
    public ResponseEntity<?> handleOperationException(final OperationException exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(exception.getStatus())
                .description(exception.getDescription())
                .response();
    }

    /**
     * Handle exception.
     *
     * @param exception the exception
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public void handleException(final AsyncRequestTimeoutException exception) {
        logger.warn(exception);
    }

    /**
     * Handle exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(final Exception exception) {
        logger.warn(exception);
        exception.printStackTrace();
        return responseBuilder()
                .status(FAILURE_INTERNAL_UNKNOWN)
                .response();
    }

    private OperationResultResponseBuilder responseBuilder() {
        return new OperationResultResponseBuilder()
                .statusMapper(operationResultStatusMapper)
                .type(OperationResult.class);
    }
}
