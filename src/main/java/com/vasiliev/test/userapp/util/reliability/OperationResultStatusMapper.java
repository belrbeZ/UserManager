package com.vasiliev.test.userapp.util.reliability;

import org.springframework.http.HttpStatus;

import java.util.function.Function;

public interface OperationResultStatusMapper extends Function<OperationResultStatus, HttpStatus> {

}
