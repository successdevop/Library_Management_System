package com.library.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static com.library.app_files.Config.ALGORITHM;

public class AuthUtils{

    private static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];

        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

            // Apply the salt first
            digest.update(generateSalt());

            // Compute the hash from the password bytes
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Encode to Base64 so it can be cleanly stored as text
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean verifyPassword(String password, String storedPassword){
        String newHash = hashPassword(password);
        return newHash.equals(storedPassword);
    }
}