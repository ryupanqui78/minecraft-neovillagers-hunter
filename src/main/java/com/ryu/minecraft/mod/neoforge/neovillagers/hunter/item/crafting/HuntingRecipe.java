package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;

public class HuntingRecipe extends SingleItemRecipe {
    
    public static final String RECIPE_NAME = "hunting";
    
    private final int count;
    private final int max;
    private final int weight;
    
    public HuntingRecipe(String pGroup, int pWeight, int pMax, Ingredient pIngredient, int pCount, ItemStack pResult) {
        super(pGroup, pIngredient, pResult);
        this.count = pCount;
        this.max = pMax;
        this.weight = pWeight;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public ItemStack getResult() {
        return this.result();
    }
    
    @Override
    public RecipeSerializer<HuntingRecipe> getSerializer() {
        return SetupRecipeSerializer.HUNTING.get();
    }
    
    @Override
    public RecipeType<HuntingRecipe> getType() {
        return SetupRecipeType.HUNTING.get();
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return SetupRecipeType.HUNTING_CATEGORY.get();
    }
    
}
