package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import java.util.function.Supplier;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister
            .create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, NeoVillagersHunter.MODID);
    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersHunter.MODID);
    
    public static final Supplier<RecipeBookCategory> HUNTING_CATEGORY = SetupRecipeType.RECIPE_BOOK_CATEGORIES
            .register("hunting", RecipeBookCategory::new);
    
    public static final Supplier<RecipeType<HuntingRecipe>> HUNTING = SetupRecipeType.REGISTER
            .register(HuntingRecipe.RECIPE_NAME, () -> RecipeType.<HuntingRecipe> simple(
                    ResourceLocation.fromNamespaceAndPath(NeoVillagersHunter.MODID, HuntingRecipe.RECIPE_NAME)));
    
    private SetupRecipeType() {
    }
}
