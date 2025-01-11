package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory;

import java.util.Optional;
import java.util.Random;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.helpers.HuntingHelper;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.slots.EmeraldSlotInput;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.slots.ItemSlotInput;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting.HuntingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupMenus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
            HuntingMenu.this.onTake();
        }
        
    }
    
    private static Random seed = new Random();
    
    public static final String MENU_NAME = "hunting";
    
    private final Level level;
    private final ContainerLevelAccess access;
    private final ResultContainer resultSlot = new ResultContainer();
    private final Container inputSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            HuntingMenu.this.slotsChanged(this);
        }
    };
    
    private ItemStack randomSelection;
    
    // Client constructor
    public HuntingMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }
    
    // Server constructor
    public HuntingMenu(int windowId, Inventory playerInventory, ContainerLevelAccess pAccess) {
        super(SetupMenus.HUNTING_CONTAINER.get(), windowId);
        
        this.level = playerInventory.player.level();
        this.access = pAccess;
        
        this.addSlot(new EmeraldSlotInput(this.inputSlots, 0, 38, 20));
        this.addSlot(new ItemSlotInput(this.inputSlots, 1, 38, 50, Items.ARROW));
        this.addSlot(new Slot(this.inputSlots, 2, 66, 35));
        
        this.addSlot(new SlotOutput(this.resultSlot, 0, 120, 35));
        
        this.addInventorySlots(playerInventory);
        this.randomSelection = HuntingHelper.selectOneRandomEgg(this.level, HuntingMenu.seed);
    }
    
    private void addInventorySlots(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + (k * 18), 142));
        }
    }
    
    public void onTake() {
        this.randomSelection = HuntingHelper.selectOneRandomEgg(this.level, HuntingMenu.seed);
        this.inputSlots.removeItem(0, 1);
        this.inputSlots.removeItem(1, 1);
        if (!this.inputSlots.getItem(2).isEmpty()) {
            final Optional<HuntingRecipe> recipe = HuntingHelper.selectRecipeFromResource(this.level,
                    this.inputSlots.getItem(2));
            if (recipe.isPresent()) {
                this.inputSlots.removeItem(2, recipe.get().getIngredient().getItems()[0].getCount());
            }
        }
        this.broadcastChanges();
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
        if (pContainer == this.inputSlots) {
            if (!this.inputSlots.getItem(0).isEmpty() && !this.inputSlots.getItem(1).isEmpty()) {
                final ItemStack resource = this.inputSlots.getItem(2);
                if (resource.isEmpty()) {
                    this.resultSlot.setItem(0, this.randomSelection);
                } else {
                    final Optional<HuntingRecipe> recipeResult = HuntingHelper.selectRecipeFromResource(this.level,
                            resource);
                    if (recipeResult.isPresent()) {
                        this.resultSlot.setItem(0, HuntingHelper.validateRecipe(recipeResult.get(), resource));
                    }
                }
            } else {
                this.resultSlot.setItem(0, ItemStack.EMPTY);
            }
            this.broadcastChanges();
        }
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(this.access, pPlayer, SetupBlocks.HUNTING_TABLE.get());
    }
    
}
