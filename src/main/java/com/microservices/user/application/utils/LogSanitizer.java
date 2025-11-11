package com.microservices.user.application.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSanitizer {

    private static final Logger log = LoggerFactory.getLogger(LogSanitizer.class);

    private static final String SANITIZED_ID = "[sanitized_id]";
    private static final String SANITIZED_EMAIL = "[sanitized_email]";

    private LogSanitizer() {
        // Empty private constructor to hide implicit public one.
    }

    public static String sanitizeId(String id) {
        if (id == null || id.isEmpty()) {
            log.warn("[UserProfileListener] Ignoring event with null/empty id");
        }
        return SANITIZED_ID;
    }

    public static String sanitizeEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email to be sanitized is null or empty");

        }
        return SANITIZED_EMAIL;
    }
}