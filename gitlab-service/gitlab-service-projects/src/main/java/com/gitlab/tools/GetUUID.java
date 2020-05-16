package com.gitlab.tools;

import java.util.UUID;

public class GetUUID {
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }
}
