package com.sis.gescobank.handler;

import com.sis.gescobank.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BusinessException.class,
            DataAlreadyExistBusinessException.class,
            DataNotCoherentBusinessException.class,
            RequiredFieldException.class,
            ValidationBusinessException.class})
    public ResponseEntity<ErrorResponse> handleConflictExceptions(Exception exception) {
        return buildResponseEntity(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundBusinessException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(DataNotFoundBusinessException exception) {
        return buildResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(Exception exception, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), status.value());
        return new ResponseEntity<>(errorResponse, status);
    }
}