package org.password.passwordcreator;
/*
 * This code is open-source and can be distributed under the MIT License.
 * Utility class for password transformations.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordUtil {
    private static final Random RANDOM = new Random();

    private static final char[][] SUBSTITUTIONS = {
            {'a', '4'}, {'A', '4'},
            {'e', '3'}, {'E', '3'},
            {'i', '1'}, {'I', '1'},
            {'o', '0'}, {'O', '0'},
            {'s', '5'}, {'S', '5'},
            {'t', '7'}, {'T', '7'}
    };

    // Special chars to pad if needed
    private static final String SPECIAL_CHARS = ".!?";

    /**
     * Convert a single character:
     * - If it's a letter and found in substitutions, use that.
     * - If it's a letter and not found, randomly uppercase/lowercase it, but keep as letter.
     * - Otherwise, keep character as is (digits or others can stay).
     */
    private static char convertChar(char c) {
        // Check if letter
        if (Character.isLetter(c)) {
            // Random uppercase/lowercase
            if (RANDOM.nextBoolean()) {
                c = Character.toLowerCase(c);
            } else {
                c = Character.toUpperCase(c);
            }

            // Try substitution
            char sub = trySubstitution(c);
            if (sub != 0) {
                return sub;
            } else {
                // No substitution found, return the character as is (already random cased)
                return c;
            }
        } else {
            // Not a letter, just return as is.
            return c;
        }
    }

    private static char trySubstitution(char c) {
        for (char[] pair : SUBSTITUTIONS) {
            if (pair[0] == c) {
                return pair[1];
            }
        }
        return 0;
    }

    /**
     * Convert a string by transforming each character.
     */
    public static String convertString(String input) {
        if (input == null || input.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(convertChar(c));
        }
        return sb.toString();
    }

    /**
     * Generate multiple passwords.
     * @param words - List of words
     * @param numbers - List of numbers (as strings)
     * @param count - how many passwords to generate
     * @param length - desired length of each password
     */
    public static List<String> generatePasswords(List<String> words, List<String> numbers, int count, int length) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String pwd = generateSinglePassword(words, numbers, length);
            result.add(pwd);
        }
        return result;
    }

    /**
     * Generate a single password from words and optional numbers.
     * Steps:
     * - Convert each word with convertString.
     * - Insert numbers in random places if present.
     * - Ensure final length by trimming or padding.
     */
    private static String generateSinglePassword(List<String> words, List<String> numbers, int length) {
        // Convert all words
        List<String> convertedWords = new ArrayList<>();
        for (String w : words) {
            convertedWords.add(convertString(w));
        }

        // Start building the password
        StringBuilder sb = new StringBuilder();

        // We'll insert words in order, and optionally insert numbers in between
        for (int i = 0; i < convertedWords.size(); i++) {
            sb.append(convertedWords.get(i));
            // With some probability, insert a number after the word if numbers exist
            if (!numbers.isEmpty() && RANDOM.nextBoolean()) {
                String num = numbers.get(RANDOM.nextInt(numbers.size()));
                sb.append(convertString(num));
            }
        }

        String pwd = sb.toString();

        // Adjust length
        if (pwd.length() > length) {
            pwd = pwd.substring(0, length);
        } else {
            // If shorter, pad with special chars until desired length
            while (pwd.length() < length) {
                pwd += SPECIAL_CHARS.charAt(RANDOM.nextInt(SPECIAL_CHARS.length()));
            }
        }

        return pwd;
    }

    public static List<String> parseNumbers(String input) {
        List<String> result = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return result;
        }
        String[] parts = input.split(",");
        for (String p : parts) {
            p = p.trim();
            if (!p.isEmpty()) {
                result.add(p);
            }
        }
        return result;
    }
}
