package com.sis.gescobank.util.exception;

import java.util.Map;
import java.util.stream.Collectors;

public final class ExceptionHelper {

    private ExceptionHelper() {
    }

    public static String format(String entityName, Map<String, String> fields, String message) {
        String formattedFields = fields.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=(" + entry.getValue() + ")")
                .collect(Collectors.joining(fields.size() > 1 ? ", " : ""));

        return entityName + " " + message + " with " + formattedFields;
    }
}
