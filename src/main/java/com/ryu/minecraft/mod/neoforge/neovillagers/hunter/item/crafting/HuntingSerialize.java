package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HuntingSerialize implements RecipeSerializer<HuntingRecipe> {
    
    private static final String ELEMENT_COUNT = "count";
    private static final String ELEMENT_GROUP = "group";
    private static final String ELEMENT_INGREDIENT = "ingredient";
    private static final String ELEMENT_MAX = "max";
    private static final String ELEMENT_RESULT = "result";
    private static final String ELEMENT_WEIGHT = "weight";
    
    private final MapCodec<HuntingRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, HuntingRecipe> streamCodec;
    
    public HuntingSerialize() {
        this.codec = RecordCodecBuilder.mapCodec(element -> element.group(
                Codec.STRING.optionalFieldOf(HuntingSerialize.ELEMENT_GROUP, "").forGetter(HuntingRecipe::group),
                Codec.INT.fieldOf(HuntingSerialize.ELEMENT_WEIGHT).forGetter(HuntingRecipe::getWeight),
                Codec.INT.fieldOf(HuntingSerialize.ELEMENT_MAX).forGetter(HuntingRecipe::getMax),
                Ingredient.CODEC.fieldOf(HuntingSerialize.ELEMENT_INGREDIENT).forGetter(HuntingRecipe::input),
                Codec.INT.fieldOf(HuntingSerialize.ELEMENT_COUNT).forGetter(HuntingRecipe::getCount),
                ItemStack.STRICT_CODEC.fieldOf(HuntingSerialize.ELEMENT_RESULT).forGetter(HuntingRecipe::getResult))
                .apply(element, HuntingRecipe::new));
        this.streamCodec = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, HuntingRecipe::group, ByteBufCodecs.INT,
                HuntingRecipe::getWeight, ByteBufCodecs.INT, HuntingRecipe::getMax, Ingredient.CONTENTS_STREAM_CODEC,
                HuntingRecipe::input, ByteBufCodecs.INT, HuntingRecipe::getCount, ItemStack.STREAM_CODEC,
                HuntingRecipe::getResult, HuntingRecipe::new);
        
    }
    
    @Override
    public MapCodec<HuntingRecipe> codec() {
        return this.codec;
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, HuntingRecipe> streamCodec() {
        return this.streamCodec;
    }
    
}
