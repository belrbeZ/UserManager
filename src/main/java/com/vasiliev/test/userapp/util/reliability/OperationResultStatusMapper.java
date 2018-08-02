package com.vasiliev.test.userapp.util.reliability;

import org.springframework.http.HttpStatus;

import java.util.function.Function;

/**
 * The interface Operation result status mapper.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
public interface OperationResultStatusMapper extends Function<OperationResultStatus, HttpStatus> {

}
