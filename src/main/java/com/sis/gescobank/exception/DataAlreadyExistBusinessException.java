package com.sis.gescobank.exception;

import com.sis.gescobank.util.exception.ExceptionHelper;

import java.util.Map;

public class DataAlreadyExistBusinessException extends RuntimeException {

    public DataAlreadyExistBusinessException(String entityName, Map<String, String> fields) {
        super(ExceptionHelper.format(entityName, fields, "already exists"));
    }
}
