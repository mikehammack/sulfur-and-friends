package com.maximarcana.sulfurfriends.guide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.SulfurCubeArchetype;

/**
 * Reads the sulfur cube archetype datapack registry and turns it into
 * display-friendly data for the field guide and bucket tooltips.
 */
public final class ArchetypeGuideData {
    public record Entry(String id, String displayName, List<Component> traits, List<Component> exampleBlocks) {
    }

    private ArchetypeGuideData() {
    }

    private static Optional<Registry<SulfurCubeArchetype>> registry() {
        var level = Minecraft.getInstance().level;
        if (level == null) {
            return Optional.empty();
        }
        return level.registryAccess().lookup(Registries.SULFUR_CUBE_ARCHETYPE);
    }

    /** All archetypes in registry order, for the field guide. */
    public static List<Entry> all() {
        List<Entry> entries = new ArrayList<>();
        registry().ifPresent(reg -> {
            for (Map.Entry<ResourceKey<SulfurCubeArchetype>, SulfurCubeArchetype> e : reg.entrySet()) {
                entries.add(toEntry(e.getKey().identifier().getPath(), e.getValue()));
            }
        });
        return entries;
    }

    /** The archetype matching the given absorbed-block item, if any. */
    public static Optional<Entry> matching(Holder<Item> itemHolder) {
        return registry().flatMap(reg -> {
            for (Map.Entry<ResourceKey<SulfurCubeArchetype>, SulfurCubeArchetype> e : reg.entrySet()) {
                SulfurCubeArchetype archetype = e.getValue();
                if (archetype.items().contains(itemHolder)) {
                    return Optional.of(toEntry(e.getKey().identifier().getPath(), archetype));
                }
            }
            return Optional.empty();
        });
    }

    private static Entry toEntry(String id, SulfurCubeArchetype archetype) {
        List<Component> traits = new ArrayList<>();
        if (archetype.explosion().isPresent()) {
            traits.add(Component.translatable("guide.sulfurandfriends.trait.explosive"));
        }
        if (archetype.contactDamage().isPresent()) {
            traits.add(Component.translatable("guide.sulfurandfriends.trait.contact_damage"));
        }
        if (archetype.buoyant()) {
            traits.add(Component.translatable("guide.sulfurandfriends.trait.buoyant"));
        }

        List<Component> examples = new ArrayList<>();
        archetype.items().stream().limit(6).forEach(holder ->
            examples.add(Component.translatable(holder.value().getDescriptionId()))
        );

        return new Entry(id, prettify(id), traits, examples);
    }

    private static String prettify(String path) {
        String[] parts = path.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(part.charAt(0)));
            sb.append(part.substring(1));
        }
        return sb.toString();
    }
}
