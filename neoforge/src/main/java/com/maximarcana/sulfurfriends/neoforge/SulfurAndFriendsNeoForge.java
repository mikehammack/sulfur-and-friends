package com.maximarcana.sulfurfriends.neoforge;

import com.maximarcana.sulfurfriends.SulfurAndFriends;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(SulfurAndFriends.MOD_ID)
public final class SulfurAndFriendsNeoForge {
    public SulfurAndFriendsNeoForge() {
        SulfurAndFriends.init();
        // ClientSetup is only ever loaded on the physical client, so the
        // dedicated server never sees client-only classes.
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            IEventBus modBus = ModList.get().getModContainerById(SulfurAndFriends.MOD_ID)
                .orElseThrow().getEventBus();
            ClientSetup.init(modBus);
        }
    }
}
