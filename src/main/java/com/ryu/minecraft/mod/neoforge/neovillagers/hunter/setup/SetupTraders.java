package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.Hunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.Worker;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@Mod.EventBusSubscriber(modid = NeoVillagersHunter.MODID)
public class SetupTraders {
    
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == SetupVillagers.HUNTER.get()) {
            final Worker worker = new Hunter();
            worker.getTrades(event);
        }
    }
    
    private SetupTraders() {
    }
    
}
