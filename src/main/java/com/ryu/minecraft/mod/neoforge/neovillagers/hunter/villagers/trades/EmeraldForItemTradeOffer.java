package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public class EmeraldForItemTradeOffer extends TradeOfferItemListing {
    private static final ItemStack SELL_ITEM = new ItemStack(Items.EMERALD);
    
    private final ItemStack itemCost;
    
    public EmeraldForItemTradeOffer(ItemLike pItemCost, int pCost, int pMaxUses, int pVillagerXp) {
        super(pMaxUses, pVillagerXp);
        
        this.itemCost = new ItemStack(pItemCost, pCost);
    }
    
    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        return new MerchantOffer(this.itemCost, EmeraldForItemTradeOffer.SELL_ITEM, this.maxUses, this.villagerXp,
                TradeOfferItemListing.PRICE_MULTIPLIER);
    }
    
}
