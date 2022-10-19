package com.nexon.maple.util.Encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    private final String SHA256 = "SHA-256";

    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA256);
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            new IllegalArgumentException(SHA256+" 암호화 방식이 존재하지 않습니다.");
        }
        return null;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
