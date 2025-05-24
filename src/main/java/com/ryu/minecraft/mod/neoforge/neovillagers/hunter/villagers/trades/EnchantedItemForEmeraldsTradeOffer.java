package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.villagers.trades;

import java.util.Optional;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
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
        final RegistryAccess registryaccess = pTrader.level().registryAccess();
        final Optional<HolderSet.Named<Enchantment>> optional = registryaccess.lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.ON_TRADED_EQUIPMENT);
        final ItemStack itemstack = EnchantmentHelper.enchantItem(pRandom, new ItemStack(this.itemSell.getItem()), i,
                registryaccess, optional);
        final int j = Math.min(this.baseEmeraldCost + i, 64);
        final ItemCost itemcost = new ItemCost(Items.EMERALD, j);
        return new MerchantOffer(itemcost, itemstack, this.maxUses, this.villagerXp,
                TradeOfferItemListing.PRICE_MULTIPLIER);
    }
    
}
