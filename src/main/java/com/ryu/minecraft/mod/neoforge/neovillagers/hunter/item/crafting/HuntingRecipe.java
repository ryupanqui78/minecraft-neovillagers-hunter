package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class HuntingRecipe extends SingleItemRecipe {
    
    public static final String RECIPE_NAME = "hunting";
    
    private final int weight;
    private final int max;
    
    public HuntingRecipe(String pGroup, int pWeight, int max, Ingredient pIngredient, ItemStack pResult) {
        super(SetupRecipeType.HUNTING.get(), SetupRecipeSerializer.HUNTING.get(), pGroup, pIngredient, pResult);
        this.weight = pWeight;
        this.max = max;
    }
    
    public Ingredient getIngredient() {
        return this.ingredient;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }
}
