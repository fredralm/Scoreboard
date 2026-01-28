package Util;

import java.util.UUID;

public class IdGenerator {
    /**
     * Generates a random ID with the given prefix, for use as primary key in a database.
     */
    public static String generate(String prefix) {
        return prefix + UUID.randomUUID();
    }
}
