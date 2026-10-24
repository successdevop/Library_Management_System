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

        // Decode the original stored hash back into a raw byte array
        byte[] originalHash = Base64.getDecoder().decode(parts[1]);

        try{
            // Re-hash the login attempt password using the ORIGINAL salt
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(salt);
            byte[] newHashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Use MessageDigest.isEqual for a secure, constant-time byte comparison
            return MessageDigest.isEqual(newHashedBytes, originalHash);
        } catch (NoSuchAlgorithmException | IllegalArgumentException ex){
            throw new RuntimeException(ex);
        }
    }
}