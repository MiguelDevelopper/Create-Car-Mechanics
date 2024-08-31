package com.Miguel_dev.Create_Vehicular_Works.compat.jei;

import com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking.PartsMakingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

import javax.annotation.Nonnull;


public class PartsMakingCategory extends CVW_RecipeCategory<PartsMakingRecipe> {

	/*private AnimatedPartsMaker PartsMaker = new AnimatedPartsMaker();

	public PartsMakingCategory(Info<PartsMakingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PartsMakingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 15, 5)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredient());

		builder
				.addSlot(RecipeIngredientRole.OUTPUT, 140, 41)
				.setBackground(getRenderedSlot(), -1, -1)
				.addItemStack(recipe.getResultItem(null));
	}

	@Override
	public void draw(PartsMakingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX,
					 double mouseY) {
		AllGuiTextures.JEI_ARROW.render(stack, 85, 45);
		AllGuiTextures.JEI_DOWN_ARROW.render(stack, 43, 4);
		PartsMaker.draw(stack, 48, 40);
	}*/

    private final AnimatedPartsMaker PartsMaker = new AnimatedPartsMaker();

	public PartsMakingCategory(Info<PartsMakingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull PartsMakingRecipe recipe, @Nonnull IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 44, 5)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		List<ProcessingOutput> results = recipe.getRollableResults();
		int i = 0;
		for (ProcessingOutput output : results) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;
			builder
					.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
					.setBackground(getRenderedSlot(output), -1, -1)
					.addItemStack(output.getStack())
					.addTooltipCallback(addStochasticTooltip(output));
			i++;
		}
	}

	@Override
	public void draw(@Nonnull PartsMakingRecipe recipe, @Nonnull IRecipeSlotsView iRecipeSlotsView, @Nonnull GuiGraphics graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
		AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);

		PartsMaker.draw(graphics, 72, 42);
	}

}

