package edu.seu.mtyx.acl.utils;

import org.apache.tomcat.util.security.MD5Encoder;

import java.nio.charset.StandardCharsets;

public class MD5 {
    public static String encrypt(String str) {
        return MD5Encoder.encode(str.getBytes(StandardCharsets.UTF_8));
    }
}
