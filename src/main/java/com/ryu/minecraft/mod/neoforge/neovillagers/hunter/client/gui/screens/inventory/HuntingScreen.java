package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.client.gui.screens.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.HuntingMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class HuntingScreen extends AbstractContainerScreen<HuntingMenu> {
    
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(NeoVillagersHunter.MODID,
            "textures/gui/container/hunting.png");
    
    public HuntingScreen(HuntingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, HuntingScreen.TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isMissingResources()) {
            final boolean isHover = this.isHovering(116, 31, 24, 24, mouseX, mouseY);
            
            graphics.blit(RenderPipelines.GUI_TEXTURED, HuntingScreen.TEXTURE, this.leftPos + 87, this.topPos + 35, 176,
                    0, 22, 15, 256, 256);
            if (isHover) {
                final int numMissing = this.menu.getNumIngredientsRequired();
                final List<Component> list = new ArrayList<>();
                if (numMissing == 255) {
                    list.add((Component.translatable("container.hunting.invalid.resource"))
                            .withStyle(ChatFormatting.RED));
                } else {
                    list.add((Component.translatable("container.hunting.missing.resource", numMissing))
                            .withStyle(ChatFormatting.RED));
                }
                graphics.setTooltipForNextFrame(this.font, list, Optional.empty(), mouseX, mouseY);
            }
        }
    }
    
}
