package com.maximarcana.sulfurfriends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SulfurAndFriends {
    public static final String MOD_ID = "sulfurandfriends";
    public static final String MOD_NAME = "Sulfur & Friends";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private SulfurAndFriends() {
    }

    public static void init() {
        LOGGER.info("{} common init", MOD_NAME);
    }
}
