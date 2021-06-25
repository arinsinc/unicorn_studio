package com.unicorn.studio.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.time.LocalDateTime;

public class TokenGenerator {
    public static String generateToken(String email) {
        Instant timeStamp = Instant.now();
        return DigestUtils.sha3_256Hex(email+timeStamp.toString());
    }

    public static LocalDateTime generateTimeWindow(int hrs) {
        LocalDateTime timeWindow = LocalDateTime.now().plusHours(hrs);
        return timeWindow;
    }
}
