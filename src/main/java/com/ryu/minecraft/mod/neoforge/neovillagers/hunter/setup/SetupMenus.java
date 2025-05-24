package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.HuntingMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupMenus {
    
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            NeoVillagersHunter.MODID);
    
    public static final DeferredHolder<MenuType<?>, MenuType<HuntingMenu>> HUNTING = SetupMenus.MENUS.register(
            HuntingMenu.MENU_NAME, () -> new MenuType<HuntingMenu>(HuntingMenu::new, FeatureFlags.DEFAULT_FLAGS));
    
    private SetupMenus() {
    }
}
