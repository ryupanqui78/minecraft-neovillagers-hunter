package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import java.util.function.Supplier;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.block.HuntingBlock;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister
            .create(BuiltInRegistries.RECIPE_DISPLAY, NeoVillagersHunter.MODID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister
            .create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, NeoVillagersHunter.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersHunter.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, NeoVillagersHunter.MODID);
    
    public static final Supplier<RecipeBookCategory> HUNTING_CATEGORY = SetupRecipeType.RECIPE_BOOK_CATEGORIES
            .register(HuntingBlock.BLOCK_NAME, RecipeBookCategory::new);
    
    public static final Supplier<RecipeType<HuntingRecipe>> HUNTING = SetupRecipeType.RECIPE_TYPES
            .register(HuntingBlock.BLOCK_NAME, () -> RecipeType.<HuntingRecipe> simple(
                    Identifier.fromNamespaceAndPath(NeoVillagersHunter.MODID, HuntingBlock.BLOCK_NAME)));
    
    public static final Supplier<RecipeSerializer<HuntingRecipe>> HUNTING_SERIALIZER = SetupRecipeType.RECIPE_SERIALIZERS
            .register(HuntingBlock.BLOCK_NAME, () -> HuntingRecipe.SERIALIZER);
    
    private SetupRecipeType() {
    }
}
