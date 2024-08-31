package com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking;

import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class PartsMakerRecipeParams extends ProcessingRecipeBuilder.ProcessingRecipeParams {

    protected PartsMakerRecipeParams(ResourceLocation id, Ingredient input, ProcessingOutput output) {
        super(id);
        if(ingredients == null) {
            ingredients =  NonNullList.create();
        }
        ingredients.add(input);
        if(results == null) {
            results = NonNullList.create();
        }
        results.add(output);
        if(fluidIngredients == null) {
            fluidIngredients = NonNullList.create();
        }
        fluidIngredients.clear();
        if(fluidResults == null) {
            fluidResults = NonNullList.create();
        }
        fluidResults.clear();
        processingDuration = 120;// Config.ROLLING_MILL_PROCESSING_DURATION.get();
        requiredHeat = HeatCondition.NONE;
        keepHeldItem = false;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public NonNullList<ProcessingOutput> getResults() {
        return results;
    }

    public int getProcessingDuration() {
        return processingDuration;
    }

    public ResourceLocation getID() {
        return id;
    }
}