package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HuntingSerialize implements RecipeSerializer<HuntingRecipe> {
    
    private static final String ELEMENT_INGREDIENT = "ingredient";
    private static final String ELEMENT_GROUP = "group";
    private static final String ELEMENT_WEIGHT = "weight";
    private static final String ELEMENT_MAX = "max";
    
    @Override
    public Codec<HuntingRecipe> codec() {
        return RecordCodecBuilder.create(elements -> elements.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, HuntingSerialize.ELEMENT_GROUP, "")
                        .forGetter(HuntingRecipe::getGroup),
                Codec.INT.optionalFieldOf(HuntingSerialize.ELEMENT_WEIGHT, 10).forGetter(HuntingRecipe::getWeight),
                Codec.INT.optionalFieldOf(HuntingSerialize.ELEMENT_MAX, 2).forGetter(HuntingRecipe::getMax),
                Ingredient.CODEC_NONEMPTY.fieldOf(HuntingSerialize.ELEMENT_INGREDIENT).forGetter(
                        HuntingRecipe::getIngredient),
                ItemStack.RESULT_CODEC.forGetter(HuntingRecipe::getResult)).apply(elements, HuntingRecipe::new));
    }
    
    @Override
    public HuntingRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        final String group = pBuffer.readUtf();
        final int weight = pBuffer.readInt();
        final int max = pBuffer.readInt();
        final Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        final ItemStack itemstack = pBuffer.readItem();
        return new HuntingRecipe(group, weight, max, ingredient, itemstack);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, HuntingRecipe pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pBuffer.writeInt(pRecipe.getWeight());
        pBuffer.writeInt(pRecipe.getMax());
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.getResult());
    }
    
}
