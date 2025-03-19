package com.sis.gescobank.exception;

import com.sis.gescobank.util.exception.ExceptionHelper;

import java.util.Map;
import java.util.stream.Collectors;

public class DataNotFoundBusinessException extends RuntimeException {

    public DataNotFoundBusinessException(String entityName, String entityId) {
        super(entityName + " not found with id=(" + entityId + ")");
    }

    public DataNotFoundBusinessException(String entityName, Map<String, String> fields) {
        super(ExceptionHelper.format(entityName, fields, "not found"));
    }
}
