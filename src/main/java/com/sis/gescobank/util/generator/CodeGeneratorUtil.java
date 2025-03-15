package com.sis.gescobank.util.generator;

import java.security.SecureRandom;

public final class CodeGeneratorUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGeneratorUtil() {
    }

    public static String generateCode(int length) {
        StringBuilder identifier = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = RANDOM.nextInt(10);
            identifier.append(digit);
        }
        return identifier.toString();
    }
}
