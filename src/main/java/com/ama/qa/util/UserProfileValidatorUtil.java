package com.ama.qa.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility to validate User Profile Name constraints:
 * User profile name must NOT contain any of the following characters: (A, C, G, I, L, K).
 * Case-insensitive comparison.
 */
public class UserProfileValidatorUtil {

    private static final char[] FORBIDDEN_CHARS = {'A', 'C', 'G', 'I', 'L', 'K'};

    /**
     * Checks if profile name is valid (does NOT contain A, C, G, I, L, K).
     *
     * @param profileName Profile name string to check
     * @return true if valid (no forbidden characters present), false otherwise
     */
    public static boolean isValidProfileName(String profileName) {
        if (profileName == null || profileName.trim().isEmpty()) {
            return false;
        }
        String upper = profileName.toUpperCase();
        for (char c : FORBIDDEN_CHARS) {
            if (upper.indexOf(c) >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of forbidden characters found in the given profile name.
     */
    public static List<Character> getForbiddenCharsFound(String profileName) {
        List<Character> found = new ArrayList<>();
        if (profileName == null) return found;

        String upper = profileName.toUpperCase();
        for (char c : FORBIDDEN_CHARS) {
            if (upper.indexOf(c) >= 0) {
                found.add(c);
            }
        }
        return found;
    }
}
