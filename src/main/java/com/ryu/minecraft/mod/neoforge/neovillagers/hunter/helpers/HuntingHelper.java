package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class HuntingHelper {
    
    public static List<HuntingRecipe> listAllRecipesOfHunting(Level pLevel) {
        final List<RecipeHolder<HuntingRecipe>> recipes = pLevel.getRecipeManager()
                .getAllRecipesFor(SetupRecipeType.HUNTING.get());
        final List<HuntingRecipe> listRecipes = new ArrayList<>();
        for (final RecipeHolder<HuntingRecipe> recipe : recipes) {
            listRecipes.add(recipe.value());
        }
        return listRecipes;
    }
    
    public static ItemStack selectEggFromResource(Level pLevel, ItemStack pItemStack) {
        final Optional<HuntingRecipe> recipe = HuntingHelper.selectRecipeFromResource(pLevel, pItemStack);
        if (recipe.isPresent()) {
            return recipe.get().getResult().copy();
        }
        return ItemStack.EMPTY;
    }
    
    public static ItemStack selectOneRandomEgg(Level pLevel, Random seed) {
        final List<HuntingRecipe> listOfEggs = HuntingHelper.listAllRecipesOfHunting(pLevel);
        final double totalWeight = listOfEggs.stream().mapToDouble(HuntingRecipe::getWeight).reduce(0.0, Double::sum);
        final double r = seed.nextDouble() * totalWeight;
        double countWeight = 0.0;
        listOfEggs.sort(Comparator.comparing(HuntingRecipe::getWeight));
        for (final HuntingRecipe item : listOfEggs) {
            countWeight += item.getWeight();
            if (countWeight >= r) {
                final int count = seed.nextInt(1, item.getMax());
                return new ItemStack(item.getResult().getItem(), count);
            }
        }
        return new ItemStack(listOfEggs.get(0).getResult().getItem(), 1);
    }
    
    public static Optional<HuntingRecipe> selectRecipeFromResource(Level pLevel, ItemStack pItemStack) {
        final List<RecipeHolder<HuntingRecipe>> listRecipes = pLevel.getRecipeManager()
                .getRecipesFor(SetupRecipeType.HUNTING.get(), new SingleRecipeInput(pItemStack), pLevel);
        if (!listRecipes.isEmpty()) {
            return Optional.of(listRecipes.get(0).value());
        }
        return Optional.empty();
    }
    
    public static ItemStack validateRecipe(HuntingRecipe recipe, ItemStack currentResource) {
        final Optional<ItemStack> itemIngredient = List.of(recipe.getIngredient().getItems()).stream()
                .filter(item -> item.is(currentResource.getItem())).findFirst();
        
        if (itemIngredient.isPresent()) {
            NeoVillagersHunter.LOGGER.info("Resource: " + itemIngredient.get().getDescriptionId());
            NeoVillagersHunter.LOGGER.info("Require: " + itemIngredient.get().getCount());
            NeoVillagersHunter.LOGGER.info("Has: " + currentResource.getCount());
            if (currentResource.getCount() == itemIngredient.get().getCount()) {
                return recipe.getResult().copy();
            }
            
        }
        return ItemStack.EMPTY;
    }
    
    private HuntingHelper() {
    }
}
