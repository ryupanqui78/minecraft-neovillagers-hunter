package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.client.gui.screens.inventory;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.HuntingMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HuntingScreen extends AbstractContainerScreen<HuntingMenu> {
    
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoVillagersHunter.MODID,
            "textures/gui/container/hunter_table.png");
    
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
        final int edgeSpacingX = (this.width - this.imageWidth) / 2;
        final int edgeSpacingY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(HuntingScreen.TEXTURE, edgeSpacingX, edgeSpacingY, 0, 0, this.imageWidth, this.imageHeight);
    }
    
}
