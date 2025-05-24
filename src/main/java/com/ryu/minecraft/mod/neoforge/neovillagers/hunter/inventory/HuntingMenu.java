package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory;

import java.util.Optional;
import java.util.Random;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.helpers.HuntingHelper;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.slots.EmeraldSlotInput;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.slots.ItemSlotInput;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class HuntingMenu extends AbstractContainerMenu {
    
    public class SlotOutput extends Slot {
        
        public SlotOutput(Container container, int index, int xPosition, int yPosition) {
            super(container, index, xPosition, yPosition);
        }
        
        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return false;
        }
        
        @Override
        public void onTake(Player player, ItemStack itemStack) {
            HuntingMenu.this.onTake(player, itemStack);
        }
        
    }
    
    public static final String MENU_NAME = "hunting";
    
    private static Random seed = new Random();
    
    private final ContainerLevelAccess access;
    private final Level level;
    private final DataSlot numIngredientsRequired = DataSlot.standalone();
    private final ResultContainer resultSlot = new ResultContainer();
    private final Container inputSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            HuntingMenu.this.slotsChanged(this);
        }
    };
    
    private final Optional<HuntingRecipe> randomSelection;
    private final boolean hasResources = true;
    
    // Client constructor
    public HuntingMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, ContainerLevelAccess.NULL);
    }
    
    // Server constructor
    public HuntingMenu(int pContainerId, Inventory pInventory, ContainerLevelAccess pAccess) {
        super(SetupMenus.HUNTING.get(), pContainerId);
        
        this.access = pAccess;
        this.level = pInventory.player.level();
        
        this.addSlot(new EmeraldSlotInput(this.inputSlots, 0, 38, 20));
        this.addSlot(new ItemSlotInput(this.inputSlots, 1, 38, 50, Items.ARROW));
        this.addSlot(new Slot(this.inputSlots, 2, 66, 35));
        
        this.addSlot(new SlotOutput(this.resultSlot, 0, 120, 35));
        
        HuntingHelper.addDefaultInventorySlots(pInventory, 8, 142, 84, this::addSlot);
        
        this.addDataSlot(this.numIngredientsRequired);
        this.randomSelection = HuntingHelper.selectOneRandomEgg(this.level, HuntingMenu.seed);
    }
    
    public boolean isMissingResources() {
        return !this.hasResources;
    }
    
    public void onTake(Player player, ItemStack itemStack) {
        final ItemStack stack = this.slots.get(0).getItem();
        final ItemStack emeraldStack = this.slots.get(1).getItem();
        final ItemStack resource = this.slots.get(2).getItem();
        itemStack.onCraftedBy(player.level(), player, itemStack.getCount());
        stack.shrink(1);
        emeraldStack.shrink(1);
        if (!resource.isEmpty()) {
            final int count = this.numIngredientsRequired.get();
            resource.shrink(count);
            this.slots.get(2).set(resource);
        }
    }
    
    private boolean quickMoveOutput(ItemStack slotItem) {
        if (this.slots.get(0).mayPlace(slotItem)) {
            return !this.moveItemStackTo(slotItem, 0, 1, false);
        } else if (this.slots.get(1).mayPlace(slotItem)) {
            return !this.moveItemStackTo(slotItem, 1, 2, false);
        } else if (this.slots.get(2).mayPlace(slotItem)) {
            return !this.moveItemStackTo(slotItem, 2, 3, false);
        }
        return false;
    }
    
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack slotItemCopy;
        final Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            final ItemStack slotItem = slot.getItem();
            final Item item = slotItem.getItem();
            slotItemCopy = slotItem.copy();
            if (pIndex < 3) { // If slot is input
                if (!this.moveItemStackTo(slotItem, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex == 3) {
                item.onCraftedBy(slotItem, pPlayer.level(), pPlayer);
                if (!this.moveItemStackTo(slotItem, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(pPlayer, slotItemCopy);
            } else if ((pIndex < 40) && this.quickMoveOutput(slotItem)) { // If slot is from player inventory
                return ItemStack.EMPTY;
            }
            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((pLevel, pPos) -> this.clearContainer(pPlayer, this.inputSlots));
    }
    
    @Override
    public void slotsChanged(Container pContainer) {
        final ItemStack itemStack = this.inputSlots.getItem(0);
        final ItemStack emeraldStack = this.inputSlots.getItem(1);
        
        this.numIngredientsRequired.set(0);
        if (!itemStack.isEmpty() && !emeraldStack.isEmpty()) {
            final ItemStack resource = this.inputSlots.getItem(2);
            if (resource.isEmpty() && this.randomSelection.isPresent()) {
                this.resultSlot.setItem(0, this.randomSelection.get().getResult().copy());
                this.numIngredientsRequired.set(this.randomSelection.get().getCount());
            } else {
                if (this.level instanceof final ServerLevel serverLevel) {
                    final Optional<RecipeHolder<HuntingRecipe>> recipeResult = serverLevel.recipeAccess()
                            .getRecipeFor(SetupRecipeType.HUNTING.get(), new SingleRecipeInput(resource), serverLevel);
                    
                    if (recipeResult.isPresent()) {
                        final HuntingRecipe recipe = recipeResult.get().value();
                        this.numIngredientsRequired.set(recipe.getCount());
                        this.resultSlot.setItem(0, recipe.getResult().copy());
                    }
                }
            }
        } else {
            this.resultSlot.setItem(0, ItemStack.EMPTY);
        }
        
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.HUNTING.get());
    }
    
}
