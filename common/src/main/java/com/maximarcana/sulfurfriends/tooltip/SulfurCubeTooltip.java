package com.maximarcana.sulfurfriends.tooltip;

import java.util.List;

import com.maximarcana.sulfurfriends.guide.ArchetypeGuideData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SulfurCubeContent;

/**
 * Appends the absorbed cube's archetype to a sulfur cube bucket's tooltip.
 * Vanilla already shows the absorbed block ("Contains: ..."); the archetype
 * is the missing piece (you can't tell "Slow Bouncy" from "Fast Flat" by
 * block name alone).
 */
public final class SulfurCubeTooltip {
    private SulfurCubeTooltip() {
    }

    public static void appendArchetypeLine(ItemStack stack, List<Component> lines) {
        SulfurCubeContent content = stack.get(DataComponents.SULFUR_CUBE_CONTENT);
        if (content == null) {
            return;
        }
        ArchetypeGuideData.matching(content.absorbedBlockItemStack().item()).ifPresent(entry ->
            lines.add(Component.translatable("tooltip.sulfurandfriends.archetype", entry.displayName())
                .withStyle(ChatFormatting.GOLD))
        );
    }
}
