package com.ryu.minecraft.mod.neoforge.neovillagers.hunter;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.client.gui.screens.inventory.HuntingScreen;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupMenus;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NeoVillagersHunter.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NeoVillagersHunter.MODID, value = Dist.CLIENT)
public class NeoVillagersHunterClient {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.HUNTING.get(), HuntingScreen::new);
    }
    
    public NeoVillagersHunterClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
