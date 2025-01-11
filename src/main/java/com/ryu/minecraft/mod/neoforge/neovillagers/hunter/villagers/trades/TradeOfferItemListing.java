package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades;

import net.minecraft.world.entity.npc.VillagerTrades;

public abstract class TradeOfferItemListing implements VillagerTrades.ItemListing {
    protected static final float PRICE_MULTIPLIER = 0.05f;
    
    protected final int maxUses;
    protected final int villagerXp;
    
    protected TradeOfferItemListing(int pMaxUSes, int pVillagerXp) {
        this.maxUses = pMaxUSes;
        this.villagerXp = pVillagerXp;
    }
}
