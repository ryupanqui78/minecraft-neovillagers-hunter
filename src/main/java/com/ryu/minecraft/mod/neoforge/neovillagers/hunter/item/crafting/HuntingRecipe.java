package com.ryu.minecraft.mod.neoforge.neovillagers.hunter.item.crafting;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryu.minecraft.mod.neoforge.neovillagers.hunter.setup.SetupRecipeType;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class HuntingRecipe implements Recipe<SingleRecipeInput> {
    
    @FunctionalInterface
    private interface Factory<T extends HuntingRecipe> {
        T create(Recipe.CommonInfo commonInfo, int weight, int max, Ingredient ingredient, ItemStackTemplate result, int count);
    }
    
    private static <T extends HuntingRecipe> MapCodec<T> simpleMapCodec(HuntingRecipe.Factory<T> factory) {
        return RecordCodecBuilder.mapCodec(i -> i.group(Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                Codec.INT.fieldOf("weight").forGetter(HuntingRecipe::weight),
                Codec.INT.fieldOf("max").forGetter(HuntingRecipe::max),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(HuntingRecipe::input),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(HuntingRecipe::result),
                Codec.INT.fieldOf("count").forGetter(HuntingRecipe::count)).apply(i, factory::create));
    }
    
    private static <T extends HuntingRecipe> StreamCodec<RegistryFriendlyByteBuf, T> simpleStreamCodec(HuntingRecipe.Factory<T> factory) {
        return StreamCodec.composite(Recipe.CommonInfo.STREAM_CODEC, o -> o.commonInfo, ByteBufCodecs.INT,
                HuntingRecipe::weight, ByteBufCodecs.INT, HuntingRecipe::max, Ingredient.CONTENTS_STREAM_CODEC,
                HuntingRecipe::input, ItemStackTemplate.STREAM_CODEC, HuntingRecipe::result, ByteBufCodecs.INT,
                HuntingRecipe::count, factory::create);
    }
    
    public static final MapCodec<HuntingRecipe> MAP_CODEC = HuntingRecipe.simpleMapCodec(HuntingRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, HuntingRecipe> STREAM_CODEC = HuntingRecipe
            .simpleStreamCodec(HuntingRecipe::new);
    public static final RecipeSerializer<HuntingRecipe> SERIALIZER = new RecipeSerializer<>(HuntingRecipe.MAP_CODEC,
            HuntingRecipe.STREAM_CODEC);
    
    protected final Recipe.CommonInfo commonInfo;
    
    private final int count;
    private final Ingredient input;
    private final int max;
    private @Nullable PlacementInfo placementInfo;
    private final ItemStackTemplate result;
    private final int weight;
    
    public HuntingRecipe(CommonInfo commonInfo, int weight, int max, Ingredient input, ItemStackTemplate result, int count) {
        this.commonInfo = commonInfo;
        this.input = input;
        this.result = result;
        this.count = count;
        this.max = max;
        this.weight = weight;
    }
    
    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        return this.result.create();
    }
    
    public int count() {
        return this.count;
    }
    
    public int max() {
        return this.max;
    }
    
    public int weight() {
        return this.weight;
    }
    
    public ItemStack getResultItem() {
        return this.result().create();
    }
    
    @Override
    public RecipeSerializer<? extends HuntingRecipe> getSerializer() {
        return SERIALIZER;
    }
    
    @Override
    public RecipeType<? extends HuntingRecipe> getType() {
        return SetupRecipeType.HUNTING.get();
    }
    
    @Override
    public String group() {
        return "";
    }
    
    public Ingredient input() {
        return this.input;
    }
    
    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input.test(input.item());
    }
    
    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.input);
        }
        
        return this.placementInfo;
    }
    
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return SetupRecipeType.HUNTING_CATEGORY.get();
    }
    
    protected ItemStackTemplate result() {
        return this.result;
    }
    
    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }
    
}
