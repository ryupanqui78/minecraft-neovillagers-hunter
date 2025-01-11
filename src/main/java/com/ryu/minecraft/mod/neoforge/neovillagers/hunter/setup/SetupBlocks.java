package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.block.HuntingBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoVillagersHunter.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoVillagersHunter.MODID);
    
    public static final DeferredBlock<HuntingBlock> HUNTING_TABLE = SetupBlocks.BLOCKS.registerBlock(
            HuntingBlock.BLOCK_NAME, HuntingBlock::new,
            BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    
    public static final DeferredItem<BlockItem> HUNTING_TABLE_ITEM = SetupBlocks.ITEMS
            .registerSimpleBlockItem(SetupBlocks.HUNTING_TABLE);
    
    private SetupBlocks() {
    }
    
}
