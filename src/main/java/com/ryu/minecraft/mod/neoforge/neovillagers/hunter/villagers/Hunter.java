package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.NeoVillagersHunter;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupVillagers;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades.EmeraldForItemTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades.EnchantedItemForEmeraldsTradeOffer;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades.ItemForEmeraldTradeOffer;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Items;

public class Hunter extends Worker {
    
    public static final String ENTITY_NAME = "hunter";
    public static final String ENTITY_POI_NAME = "hunter_poi";
    
    public static VillagerProfession registerVillager() {
        final ResourceLocation villagerResource = ResourceLocation.fromNamespaceAndPath(NeoVillagersHunter.MODID,
                Hunter.ENTITY_NAME);
        final Component villager = Component
                .translatable("entity." + villagerResource.getNamespace() + ".villager." + villagerResource.getPath());
        return new VillagerProfession(villager, x -> x.is(SetupVillagers.HUNTER_POI.getKey()),
                x -> x.is(SetupVillagers.HUNTER_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_FLETCHER);
    }
    
    @Override
    protected ItemListing[] getLevel1() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.BONE, 24, 24, 2);
        final ItemListing option2 = new EmeraldForItemTradeOffer(Items.FEATHER, 24, 24, 2);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.WOODEN_SWORD, 1, 1, 3, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel2() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.SPIDER_EYE, 16, 16, 4);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.SLIME_BALL, 3, 2, 8, 4);
        final ItemListing option3 = new ItemForEmeraldTradeOffer(Items.STONE_SWORD, 1, 1, 5, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3 };
    }
    
    @Override
    protected ItemListing[] getLevel3() {
        final ItemListing option1 = new EmeraldForItemTradeOffer(Items.GUNPOWDER, 16, 16, 4);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.ARROW, 1, 16, 12, 4);
        final ItemListing option3 = new EnchantedItemForEmeraldsTradeOffer(Items.IRON_SWORD, 5, 3, 10);
        
        return new VillagerTrades.ItemListing[] { option1, option2, option3, };
    }
    
    @Override
    protected ItemListing[] getLevel4() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.MAGMA_CREAM, 2, 1, 16, 4);
        final ItemListing option2 = new ItemForEmeraldTradeOffer(Items.BLAZE_ROD, 3, 1, 16, 8);
        
        return new VillagerTrades.ItemListing[] { option1, option2, };
    }
    
    @Override
    protected ItemListing[] getLevel5() {
        final ItemListing option1 = new ItemForEmeraldTradeOffer(Items.GHAST_TEAR, 6, 1, 8, 8);
        final ItemListing option2 = new EnchantedItemForEmeraldsTradeOffer(Items.DIAMOND_SWORD, 18, 3, 15);
        
        return new VillagerTrades.ItemListing[] { option1, option2, };
    }
    
}
