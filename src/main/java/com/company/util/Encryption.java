package com.company.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Properties;
import java.util.Random;

public class Encryption {

    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);
        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    @SneakyThrows
    public static String encryptAes(String sessionId, String userId) {
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        String s = property.getProperty("aes.salt");
        byte[] iv = new byte[16];
        byte[] salt = new byte[16];
        salt = s.getBytes();
        String value = sessionId + userId;
        IvParameterSpec ivP = new IvParameterSpec(iv);
        SecretKeySpec skeySpec = new SecretKeySpec(salt, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivP);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.encode(encrypted);
    }
}