package FairPlay.data;

import FairPlay.log.Logger;

import java.util.Random;

public class UsernameGenerator {
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    private static final int STRING_LENGTH = 12;
    private static final Random random = new Random();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }
        if (sb.toString().length() > 16) {
            Logger.log("[Error] Invalid string length");
            throw new RuntimeException("Invalid string length!");
        }
        return sb.toString();
    }
}
