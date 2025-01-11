package com.ryu.minecraft.mod.neoforge.neovillagers.hunter;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupVillagers;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(NeoVillagersHunter.MODID)
public class NeoVillagersHunter {
    
    public static final String MODID = "neovillagershunter";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public NeoVillagersHunter(IEventBus modEventBus) {
        SetupBlocks.ITEMS.register(modEventBus);
        SetupBlocks.BLOCKS.register(modEventBus);
        
        SetupMenus.MENUS.register(modEventBus);
        
        SetupVillagers.register(modEventBus);
        
        SetupRecipeType.REGISTER.register(modEventBus);
        SetupRecipeSerializer.REGISTER.register(modEventBus);
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }
    
    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(SetupBlocks.HUNTING_TABLE);
        }
    }
}
