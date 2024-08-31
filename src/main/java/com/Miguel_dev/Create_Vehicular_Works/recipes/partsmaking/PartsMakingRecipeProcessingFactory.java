package com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class PartsMakingRecipeProcessingFactory implements ProcessingRecipeBuilder.ProcessingRecipeFactory<PartsMakingRecipe> {
    @Override
    public PartsMakingRecipe create(ProcessingRecipeBuilder.ProcessingRecipeParams processingRecipeParams) {
        var params = (PartsMakerRecipeParams) processingRecipeParams;
        Ingredient ingredient = Ingredient.EMPTY;
        ItemStack result = ItemStack.EMPTY;
        if (!params.getIngredients().isEmpty()) {
            ingredient = params.getIngredients().get(0);
        }
        if (!params.getResults().isEmpty()) {
            result = params.getResults().get(0).getStack();
        }
        return new PartsMakingRecipe(ingredient, result, params.getID());
    }
}