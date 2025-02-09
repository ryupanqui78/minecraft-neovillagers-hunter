package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.client.gui.screens.inventory.HuntingScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = NeoVillagersHunter.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SetupClientModEvents {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.HUNTING_CONTAINER.get(), HuntingScreen::new);
    }
    
    private SetupClientModEvents() {
        
    }
}
