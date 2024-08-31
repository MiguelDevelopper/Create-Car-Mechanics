package com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PartsMakingRecipeInfo implements IRecipeTypeInfo {

    private ResourceLocation id;
    private SequencedAssemblyPartsMakingRecipeSerializer serializer;
    private RecipeType<PartsMakingRecipe> type;

    public PartsMakingRecipeInfo(ResourceLocation id, SequencedAssemblyPartsMakingRecipeSerializer serializer, RecipeType<PartsMakingRecipe> type) {
        this.id = id;
        this.serializer = serializer;
        this.type = type;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializer;
    }

    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type;
    }
}