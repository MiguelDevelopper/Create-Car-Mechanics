package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import java.util.List;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import com.Miguel_dev.Create_Vehicular_Works.CVW_Blocks;
import com.Miguel_dev.Create_Vehicular_Works.CVW_RecipeTypes;
import com.simibubi.create.compat.recipeViewerCommon.SequencedAssemblySubCategoryType;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.utility.Lang;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
public class PartsMakingRecipe extends ProcessingRecipe<Container> implements IAssemblyRecipe {

	public PartsMakingRecipe(ProcessingRecipeParams params) {
		super(CVW_RecipeTypes.PARTSMAKING, params);
	}

	@Override
	public boolean matches(Container inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getItem(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 4;
	}

	@Override
	protected boolean canSpecifyDuration() {
		return true;
	}

	@Override
	public void addAssemblyIngredients(List<Ingredient> list) {}

	@Override
	@Environment(EnvType.CLIENT)
	public Component getDescriptionForAssembly() {
		return Lang.translateDirect("create_vehicular_works.recipe.assembly.vehicle_parts_making");
	}

	@Override
	public void addRequiredMachines(Set<ItemLike> list) {
		list.add(CVW_Blocks.VEHICLE_PARTS_MAKER.get());
	}

	@Override
	public SequencedAssemblySubCategoryType getJEISubCategory() {
		return SequencedAssemblySubCategoryType.CUTTING;
	}

}