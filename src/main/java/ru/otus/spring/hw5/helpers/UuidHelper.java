package ru.otus.spring.hw5.helpers;

import java.util.UUID;

public class UuidHelper {
    public static UUID getUuid(String uuidStr) {
        if (uuidStr == null) { return null;}
        return UUID.fromString(uuidStr);
    }

    public static UUID getUuid(byte[] uuidBytes) {
        if (uuidBytes == null) { return null;}
        return UUID.nameUUIDFromBytes(uuidBytes);
    }

    public static UUID newUuid() {
        return UUID.randomUUID();
    }
}
