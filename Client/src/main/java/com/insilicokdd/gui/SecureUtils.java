package com.insilicokdd.gui;

import java.util.UUID;

public class SecureUtils {
    public static final int PASSWORD_LENGTH = 8;

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static boolean is_Valid_Password(String password) {

        if (password.length() < PASSWORD_LENGTH)
            return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch))
                numCount++;
            else if (is_Letter(ch))
                charCount++;
            else
                return false;
        }

        return (charCount >= 2 && numCount >= 2);
    }

    private static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    private static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }
}