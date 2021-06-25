package com.unicorn.studio.utils;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.UUID;

public class IdGenerator {
    public static String generateUId() {
        return String.valueOf(UUID.randomUUID());
    }
}
