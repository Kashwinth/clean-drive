package com.cleandrive.service;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class HashGenerator {
    public String generateHash(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteArray = new byte[8192];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            // Safe fallback hash when file is locked or cannot be read
            return "ERR_HASH_" + file.getName() + "_" + file.length() + "_" + file.lastModified();
        }
    }
}
