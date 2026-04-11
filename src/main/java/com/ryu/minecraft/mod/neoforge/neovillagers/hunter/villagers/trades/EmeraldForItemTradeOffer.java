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

public class EmeraldForItemTradeOffer extends TradeOfferItemListing {
    private static final ItemStack SELL_ITEM = new ItemStack(Items.EMERALD);
    
    private final ItemCost itemCost;
    
    public EmeraldForItemTradeOffer(ItemLike pItemCost, int pCost, int pMaxUses, int pVillagerXp) {
        super(pMaxUses, pVillagerXp);
        
        this.itemCost = new ItemCost(pItemCost, pCost);
    }
    
    @Override
    public @Nullable MerchantOffer getOffer(ServerLevel level, Entity entity, RandomSource random) {
        return new MerchantOffer(this.itemCost, EmeraldForItemTradeOffer.SELL_ITEM, this.maxUses, this.villagerXp,
                TradeOfferItemListing.PRICE_MULTIPLIER);
    }
    
}