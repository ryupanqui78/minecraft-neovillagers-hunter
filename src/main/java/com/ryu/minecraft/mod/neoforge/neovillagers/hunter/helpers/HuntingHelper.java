package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class HuntingHelper {
    
    private static final int COLUMNS_PER_ROW = 9;
    private static final int SIZE_SLOT = 18;
    
    public static void addDefaultInventorySlots(Inventory pInventory, int pSlotPosX, int pSlotHotbarPosY, int pSlotInventoryPosY, Consumer<Slot> pMenu) {
        int indexInventory = 0;
        
        for (int k = 0; k < HuntingHelper.COLUMNS_PER_ROW; k++) {
            final int posX = pSlotPosX + (k * HuntingHelper.SIZE_SLOT);
            pMenu.accept(new Slot(pInventory, indexInventory, posX, pSlotHotbarPosY));
            indexInventory++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < HuntingHelper.COLUMNS_PER_ROW; j++) {
                final int posX = pSlotPosX + (j * HuntingHelper.SIZE_SLOT);
                pMenu.accept(
                        new Slot(pInventory, indexInventory, posX, pSlotInventoryPosY + (i * HuntingHelper.SIZE_SLOT)));
                indexInventory++;
            }
        }
    }
    
    public static Optional<HuntingRecipe> selectOneRandomEgg(Level pLevel, Random seed) {
        if (pLevel instanceof final ServerLevel serverLevel) {
            final Collection<RecipeHolder<HuntingRecipe>> recipes = serverLevel.recipeAccess().recipeMap()
                    .byType(SetupRecipeType.HUNTING.get());
            final List<HuntingRecipe> listOfEggs = recipes.stream().map(e -> e.value())
                    .collect(Collectors.toCollection(ArrayList::new));
            final double totalWeight = listOfEggs.stream().mapToDouble(HuntingRecipe::getWeight).reduce(0.0,
                    Double::sum);
            final double r = seed.nextDouble() * totalWeight;
            double countWeight = 0.0;
            listOfEggs.sort(Comparator.comparing(HuntingRecipe::getWeight));
            for (final HuntingRecipe item : listOfEggs) {
                countWeight += item.getWeight();
                if (countWeight >= r) {
                    return Optional.of(item);
                }
            }
        }
        return Optional.empty();
    }
    
    private HuntingHelper() {
    }
}
