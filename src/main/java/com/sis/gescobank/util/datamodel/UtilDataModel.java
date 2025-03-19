package com.sis.gescobank.util.datamodel;

import static java.util.Objects.nonNull;

public final class UtilDataModel {
    public static final String DELIMITER_COMMA = ",";

    private UtilDataModel() {
    }

    public static String defaultToStringForEntity(Object entity, String... values) {
        return entity.getClass().getSimpleName() + "(" + defaultToStringForValues(values) + ")";
    }

    public static String defaultToStringForValues(String... values) {
        return defaultToStringForValuesWithDelimiter(values);
    }

    private static String defaultToStringForValuesWithDelimiter(String... values) {
        StringBuilder defaultString = new StringBuilder();
        if (values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (nonNull(values[i]) && !values[i].isEmpty()) {
                    if (i > 0) {
                        defaultString.append(DELIMITER_COMMA);
                    }
                    defaultString.append(values[i]);
                }
            }
        }
        return defaultString.toString();
    }

}