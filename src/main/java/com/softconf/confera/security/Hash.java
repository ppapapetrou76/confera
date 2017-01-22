package com.softconf.confera.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public final class Hash {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hash.class);

    private Hash() {

    }

    private static String digest(String in, String digest) {
        try {
            MessageDigest md = java.security.MessageDigest.getInstance(digest);
            byte[] data = getStringBytes(in);
            byte[] array = md.digest(data);
            return getStringFromByteArray(array);
        } catch (java.security.NoSuchAlgorithmException e) {
            LOGGER.error("Hash error for " + in, e);
        }
        return null;
    }

    public static String getStringFromByteArray(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte anArray : array) {
            sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static byte[] getStringBytes(String in) {
        return in.getBytes(StandardCharsets.UTF_8);
    }

    public static String getMD5(String in) {
        return digest(in, "MD5");
    }

    public static String getSHA512(String in) {
        return digest(in, "SHA-512");
    }

    public static String getCRC32(String in) {
        CRC32 crc32 = new CRC32();
        crc32.update(getStringBytes(in));
        return Long.toHexString(crc32.getValue());
    }

}
