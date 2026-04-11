package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades;

import org.jspecify.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class ItemForEmeraldTradeOffer extends TradeOfferItemListing {
    private final ItemCost itemCost;
    private final ItemStack itemSell;
    
    public ItemForEmeraldTradeOffer(ItemLike pItemSell, int pEmeraldCost, int pCount, int pMaxUses, int pVillagerXp) {
        super(pMaxUses, pVillagerXp);
        
        this.itemCost = new ItemCost(Items.EMERALD, pEmeraldCost);
        this.itemSell = new ItemStack(pItemSell, pCount);
    }
    
    @Override
    public @Nullable MerchantOffer getOffer(ServerLevel level, Entity entity, RandomSource random) {
        return new MerchantOffer(this.itemCost, this.itemSell, this.maxUses, this.villagerXp,
                TradeOfferItemListing.PRICE_MULTIPLIER);
    }
    
}
