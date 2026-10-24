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
            byte[] salt = generateSalt();

            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);

            // Apply the salt first
            digest.update(salt);

            // Compute the hash from the password bytes
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert both salt and hash to Base64 strings
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String hashStr = Base64.getEncoder().encodeToString(hashedBytes);

            // Return them combined together, separated by a colon
            return saltStr+":"+hashStr;
        } catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean verifyPassword(String password, String storedPassword){
        // Split the stored string back into the salt and the original hash
        String[] parts = storedPassword.split(":");
        if (parts.length != 2){
            return false;  // Malformed stored password string
        }

        // Decode the original salt from Base64 back into bytes
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String originalHash = parts[1];

        String newHash = hashPassword(password);
        return newHash.equals(storedPassword);
    }
}