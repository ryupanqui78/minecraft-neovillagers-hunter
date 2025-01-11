package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.inventory.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmeraldSlotInput extends Slot {
    
    public EmeraldSlotInput(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }
    
    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() == Items.EMERALD;
    }
}
