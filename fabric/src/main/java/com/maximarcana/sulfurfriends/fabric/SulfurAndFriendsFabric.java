package com.maximarcana.sulfurfriends.fabric;

import com.maximarcana.sulfurfriends.SulfurAndFriends;
import net.fabricmc.api.ModInitializer;

public final class SulfurAndFriendsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SulfurAndFriends.init();
    }
}
