package com.maximarcana.sulfurfriends.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

/** Client keybindings for the mod (registered per-loader). */
public final class GuideKeys {
    public static final KeyMapping.Category CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("sulfurandfriends", "guide"));

    public static final KeyMapping OPEN_GUIDE = new KeyMapping(
        "key.sulfurandfriends.open_guide",
        InputConstants.Type.KEYBOARD,
        InputConstants.KEY_G,
        CATEGORY
    );

    private GuideKeys() {
    }
}
