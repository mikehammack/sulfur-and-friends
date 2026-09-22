package com.maximarcana.sulfurfriends.neoforge;

import com.maximarcana.sulfurfriends.client.ArchetypeGuideScreen;
import com.maximarcana.sulfurfriends.client.GuideKeys;
import com.maximarcana.sulfurfriends.tooltip.SulfurCubeTooltip;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/** Client-only setup. Loaded exclusively on the physical client. */
final class ClientSetup {
    private ClientSetup() {
    }

    static void init(IEventBus modBus) {
        modBus.addListener((RegisterKeyMappingsEvent event) -> event.register(GuideKeys.OPEN_GUIDE));

        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> {
            while (GuideKeys.OPEN_GUIDE.consumeClick()) {
                Minecraft.getInstance().setScreenAndShow(new ArchetypeGuideScreen());
            }
        });

        NeoForge.EVENT_BUS.addListener((ItemTooltipEvent event) ->
            SulfurCubeTooltip.appendArchetypeLine(event.getItemStack(), event.getToolTip()));
    }
}
