package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingSerialize;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeSerializer {
    
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, NeoVillagersHunter.MODID);
    
    public static final DeferredHolder<RecipeSerializer<?>, HuntingSerialize> HUNTING = SetupRecipeSerializer.REGISTER
            .register(HuntingRecipe.RECIPE_NAME, HuntingSerialize::new);
    
    private SetupRecipeSerializer() {
    }
}
