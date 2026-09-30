package com.ama.qa.util;

/**
 * Utility class to validate username constraints:
 * 1. Must contain exactly 10 characters.
 * 2. Must not contain any special characters (alphanumeric only).
 */
public class UsernameValidatorUtil {

    /**
     * Checks if the given username contains exactly 10 characters
     * and has no special characters.
     *
     * @param username The username string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        // Exactly 10 characters, only letters and digits (a-z, A-Z, 0-9)
        return username.matches("^[a-zA-Z0-9]{10}$");
    }

    /**
     * Validates the username and throws an IllegalArgumentException if invalid.
     *
     * @param username The username string to validate
     */
    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (username.length() != 10) {
            throw new IllegalArgumentException("Username must contain exactly 10 characters. Current length: " + username.length());
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Username cannot contain any special characters. Provided username: " + username);
        }
    }
}
