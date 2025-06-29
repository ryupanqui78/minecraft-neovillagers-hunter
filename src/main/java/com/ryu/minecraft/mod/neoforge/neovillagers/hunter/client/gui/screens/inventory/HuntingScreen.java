package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.client.gui.screens.inventory;

import java.util.ArrayList;
import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.HuntingMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HuntingScreen extends AbstractContainerScreen<HuntingMenu> {
    
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoVillagersHunter.MODID,
            "textures/gui/container/hunting.png");
    
    public HuntingScreen(HuntingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(RenderType::guiTextured, HuntingScreen.TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isMissingResources()) {
            final boolean isHover = this.isHovering(116, 31, 24, 24, pMouseX, pMouseY);
            
            pGuiGraphics.blit(RenderType::guiTextured, HuntingScreen.TEXTURE, this.leftPos + 87, this.topPos + 35, 176,
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
                pGuiGraphics.renderComponentTooltip(this.font, list, pMouseX, pMouseY);
            }
        }
    }
    
}
