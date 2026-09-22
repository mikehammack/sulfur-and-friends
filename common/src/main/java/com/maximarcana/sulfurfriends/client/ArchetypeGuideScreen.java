package com.maximarcana.sulfurfriends.client;

import java.util.ArrayList;
import java.util.List;

import com.maximarcana.sulfurfriends.guide.ArchetypeGuideData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

/**
 * In-game field guide: every sulfur cube archetype, its traits, and example
 * blocks that produce it. Data comes from the live archetype registry, so
 * datapack-defined archetypes show up too.
 */
public class ArchetypeGuideScreen extends Screen {
    private static final int ENTRY_GAP = 10;
    private static final int LIST_TOP = 44;
    private static final int LIST_BOTTOM_MARGIN = 16;

    private record RenderedEntry(Component name, List<FormattedCharSequence> detailLines, int height) {
    }

    private List<RenderedEntry> rendered = List.of();
    private int totalHeight;
    private double scroll;

    public ArchetypeGuideScreen() {
        super(Component.translatable("guide.sulfurandfriends.title"));
    }

    @Override
    protected void init() {
        List<ArchetypeGuideData.Entry> entries = ArchetypeGuideData.all();
        int listWidth = listWidth();
        List<RenderedEntry> out = new ArrayList<>();
        for (ArchetypeGuideData.Entry entry : entries) {
            Component name = Component.literal(entry.displayName())
                .withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);

            List<FormattedCharSequence> details = new ArrayList<>();
            Component traitsLine = entry.traits().isEmpty()
                ? Component.translatable("guide.sulfurandfriends.traits",
                    Component.translatable("guide.sulfurandfriends.trait.none"))
                : Component.translatable("guide.sulfurandfriends.traits", join(entry.traits()));
            details.addAll(this.font.split(traitsLine, listWidth));

            Component absorbsLine = entry.exampleBlocks().isEmpty()
                ? Component.translatable("guide.sulfurandfriends.absorbs",
                    Component.translatable("guide.sulfurandfriends.absorbs.unknown"))
                : Component.translatable("guide.sulfurandfriends.absorbs", join(entry.exampleBlocks()));
            details.addAll(this.font.split(absorbsLine, listWidth));

            int height = this.font.lineHeight + 3 + details.size() * this.font.lineHeight + ENTRY_GAP;
            out.add(new RenderedEntry(name, details, height));
        }
        this.rendered = out;
        this.totalHeight = out.stream().mapToInt(RenderedEntry::height).sum();
        this.scroll = 0;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(extractor, mouseX, mouseY, partialTick);
        int listBottom = this.height - LIST_BOTTOM_MARGIN;
        int listLeft = (this.width - listWidth()) / 2;

        extractor.centeredText(this.font, this.title, this.width / 2, 22, 0xFFFFFF);

        var text = extractor.textRenderer();
        int y = LIST_TOP - (int) this.scroll;
        for (RenderedEntry entry : this.rendered) {
            if (y + entry.height() >= LIST_TOP && y <= listBottom) {
                int lineY = y;
                if (lineY + this.font.lineHeight >= LIST_TOP && lineY <= listBottom) {
                    text.accept(listLeft, lineY, entry.name());
                }
                lineY += this.font.lineHeight + 3;
                for (FormattedCharSequence line : entry.detailLines()) {
                    if (lineY + this.font.lineHeight >= LIST_TOP && lineY <= listBottom) {
                        text.accept(listLeft, lineY, line);
                    }
                    lineY += this.font.lineHeight;
                }
            }
            y += entry.height();
        }

        // Scrollbar.
        int listHeight = listBottom - LIST_TOP;
        if (this.totalHeight > listHeight) {
            int barX = listLeft + listWidth() + 6;
            extractor.fill(barX, LIST_TOP, barX + 4, listBottom, 0x66111111);
            int thumbH = Math.max(20, (int) (listHeight * (listHeight / (double) this.totalHeight)));
            int thumbY = LIST_TOP + (int) ((listHeight - thumbH) * (this.scroll / maxScroll()));
            extractor.fill(barX, thumbY, barX + 4, thumbY + thumbH, 0xFFAAAAAA);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll = clampScroll(this.scroll - scrollY * 24);
        return true;
    }

    private int listWidth() {
        return Math.min(380, this.width - 48);
    }

    private double maxScroll() {
        return Math.max(0, this.totalHeight - (this.height - LIST_BOTTOM_MARGIN - LIST_TOP));
    }

    private double clampScroll(double value) {
        return Math.max(0, Math.min(maxScroll(), value));
    }

    private static Component join(List<Component> parts) {
        MutableComponent out = Component.empty();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                out.append(Component.literal(", "));
            }
            out.append(parts.get(i));
        }
        return out;
    }
}
