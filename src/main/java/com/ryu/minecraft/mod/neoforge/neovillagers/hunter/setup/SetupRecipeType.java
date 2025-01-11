package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersHunter.MODID);
    
    public static final DeferredHolder<RecipeType<?>, RecipeType<HuntingRecipe>> HUNTING = SetupRecipeType.REGISTER
            .register(HuntingRecipe.RECIPE_NAME, () -> new RecipeType<HuntingRecipe>() {
            });
    
    private SetupRecipeType() {
    }
}
