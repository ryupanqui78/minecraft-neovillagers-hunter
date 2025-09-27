package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import java.util.function.Supplier;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.Hunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.Worker;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = NeoVillagersHunter.MODID)
public class SetupGeneralEvents {
    
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == SetupVillagers.HUNTER) {
            SetupGeneralEvents.registerTrades(Hunter::new, event);
        }
    }
    
    private static void registerTrades(Supplier<Worker> lazyValue, VillagerTradesEvent event) {
        lazyValue.get().getTrades(event);
    }
    
    private SetupGeneralEvents() {
    }
    
}
