package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HuntingSerialize implements RecipeSerializer<HuntingRecipe> {
    
    private static final String ELEMENT_INGREDIENT = "ingredient";
    private static final String ELEMENT_GROUP = "group";
    private static final String ELEMENT_WEIGHT = "weight";
    private static final String ELEMENT_MAX = "max";
    private static final String ELEMENT_RESULT = "result";
    private static final Codec<Ingredient> INGREDIENT_CODEC = ItemStack.CODEC.xmap(Ingredient::of,
            ingredient -> ingredient.getItems()[0]);
    
    @Override
    public MapCodec<HuntingRecipe> codec() {
        return RecordCodecBuilder.mapCodec(elements -> elements
                .group(Codec.STRING.optionalFieldOf(HuntingSerialize.ELEMENT_GROUP, "")
                        .forGetter(HuntingRecipe::getGroup),
                        Codec.INT.lenientOptionalFieldOf(ELEMENT_WEIGHT, 10).forGetter(HuntingRecipe::getWeight),
                        Codec.INT.lenientOptionalFieldOf(ELEMENT_MAX, 2).forGetter(HuntingRecipe::getMax),
                        HuntingSerialize.INGREDIENT_CODEC.fieldOf(HuntingSerialize.ELEMENT_INGREDIENT)
                                .forGetter(HuntingRecipe::getIngredient),
                        ItemStack.CODEC.fieldOf(ELEMENT_RESULT).forGetter(HuntingRecipe::getResult))
                .apply(elements, HuntingRecipe::new));
    }
    
    private HuntingRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
        final String group = pBuffer.readUtf();
        final int weight = pBuffer.readInt();
        final int max = pBuffer.readInt();
        final Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
        final ItemStack itemstack = ItemStack.STREAM_CODEC.decode(pBuffer);
        return new HuntingRecipe(group, weight, max, ingredient, itemstack);
    }
    
    private void toNetwork(RegistryFriendlyByteBuf pBuffer, HuntingRecipe pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pBuffer.writeInt(pRecipe.getWeight());
        pBuffer.writeInt(pRecipe.getMax());
        Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.getIngredient());
        ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.getResult());
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, HuntingRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }
    
}
