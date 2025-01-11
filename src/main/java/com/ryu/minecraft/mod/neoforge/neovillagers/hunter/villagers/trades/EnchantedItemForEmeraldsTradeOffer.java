package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

public class EnchantedItemForEmeraldsTradeOffer extends TradeOfferItemListing {
    private final ItemStack itemSell;
    private final int baseEmeraldCost;
    
    public EnchantedItemForEmeraldsTradeOffer(Item itemSell, int baseEmeraldCost, int pMaxUses, int pVillagerXp) {
        super(pMaxUses, pVillagerXp);
        this.itemSell = new ItemStack(itemSell);
        this.baseEmeraldCost = baseEmeraldCost;
    }
    
    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        final int i = 5 + pRandom.nextInt(15);
        final ItemStack itemstack = EnchantmentHelper.enchantItem(pRandom, new ItemStack(this.itemSell.getItem()), i,
                false);
        final int j = Math.min(this.baseEmeraldCost + i, 64);
        final ItemStack itemcost = new ItemStack(Items.EMERALD, j);
        return new MerchantOffer(itemcost, itemstack, this.maxUses, this.villagerXp,
                TradeOfferItemListing.PRICE_MULTIPLIER);
    }
    
}
