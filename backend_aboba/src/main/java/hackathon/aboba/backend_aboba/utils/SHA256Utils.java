package hackathon.aboba.backend_aboba.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hackathon.aboba.backend_aboba.exception.UnableToStartServerException;

public class SHA256Utils {

    private static final MessageDigest digest;

    private SHA256Utils() {
    }

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToStartServerException("SHA-256 algorithm is not available");
        }
    }

    public static String calculateSHA256(String input) {
        byte[] hash = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
