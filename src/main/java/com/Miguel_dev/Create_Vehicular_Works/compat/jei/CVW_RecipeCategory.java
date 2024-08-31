package com.Miguel_dev.Create_Vehicular_Works.compat.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import net.minecraft.world.item.crafting.Recipe;

public abstract class CVW_RecipeCategory<T extends Recipe<?>> extends CreateRecipeCategory<T> {

	public CVW_RecipeCategory(Info<T> info) {
		super(info);
	}
/*
	@Override
	public Component getTitle() {
		return new TranslatableComponent( CreateAddition.MODID + ".recipe." + name);
	}
 */
}