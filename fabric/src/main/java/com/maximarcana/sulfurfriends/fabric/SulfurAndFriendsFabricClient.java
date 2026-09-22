package com.maximarcana.sulfurfriends.fabric;

import com.maximarcana.sulfurfriends.client.ArchetypeGuideScreen;
import com.maximarcana.sulfurfriends.client.GuideKeys;
import com.maximarcana.sulfurfriends.tooltip.SulfurCubeTooltip;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

public final class SulfurAndFriendsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(GuideKeys.OPEN_GUIDE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GuideKeys.OPEN_GUIDE.consumeClick()) {
                client.setScreenAndShow(new ArchetypeGuideScreen());
            }
        });

        ItemTooltipCallback.EVENT.register(
            (stack, context, type, lines) -> SulfurCubeTooltip.appendArchetypeLine(stack, lines)
        );
    }
}
