package ru.job4j.dream.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHash {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordHash.class.getName());

    public String generatePasswordHash(String password, String email,
                                       int iterations, int keyLength, String algorithm) {
        char[] charsPassword = password.toCharArray();
        byte[] salt = generateSalt(email);
        String generatePasswordHash = "";
        try {
            KeySpec keySpec = new PBEKeySpec(charsPassword, salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            byte[] hash = skf.generateSecret(keySpec).getEncoded();
            generatePasswordHash = toHex(algorithm.getBytes(StandardCharsets.UTF_8))
                    + "$" + iterations + "$" + toHex(salt) + "$" + toHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return generatePasswordHash;
    }

    private byte[] generateSalt(String email) {
        return (email.replaceAll("\\.", "").split("@", 2)[1]
                + email.replaceAll("\\.", "").split("@", 2)[0])
                .getBytes(StandardCharsets.UTF_8);
    }


    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split("\\$");
        char[] charsOriginalPassword = originalPassword.toCharArray();
        String algorithm = new String(fromHex(parts[0]));
        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = fromHex(parts[2]);
        byte[] hash = fromHex(parts[3]);

        PBEKeySpec spec = new PBEKeySpec(charsOriginalPassword, salt, iterations,
                hash.length * 8);
        int diff = 1;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            byte[] testHash = skf.generateSecret(spec).getEncoded();
            diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}

