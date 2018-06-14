package com.projects.ricefactory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RfUtils {

    private static final Logger logger = LoggerFactory.getLogger(RfUtils.class);

    /* Basically convert the string into bytes and then hash the bytes.
    Note that the result of the hash would also be arbitrary binary data,
    and if we want to represent that in a string, we should use base64 or hex */

    public static String hashString(String text) {
        String encodedString = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            encodedString = Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException nsae) {
            logger.error("Exception while generating SHA-256");
        }

        return encodedString;
    }
}
