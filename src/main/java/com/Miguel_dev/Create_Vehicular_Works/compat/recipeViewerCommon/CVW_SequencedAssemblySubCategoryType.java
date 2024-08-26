package com.Miguel_dev.Create_Vehicular_Works.compat.recipeViewerCommon;

import com.simibubi.create.compat.emi.EmiSequencedAssemblySubCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.JeiSequencedAssemblySubCategory;
import com.simibubi.create.compat.rei.category.sequencedAssembly.ReiSequencedAssemblySubCategory;

import java.util.function.Supplier;

public record CVW_SequencedAssemblySubCategoryType(Supplier<Supplier<JeiSequencedAssemblySubCategory>> jei,
											   Supplier<Supplier<ReiSequencedAssemblySubCategory>> rei,
											   Supplier<Supplier<EmiSequencedAssemblySubCategory>> emi) {

	public static final CVW_SequencedAssemblySubCategoryType PRESSING = new CVW_SequencedAssemblySubCategoryType(
			() -> JeiSequencedAssemblySubCategory.AssemblyPressing::new,
			() -> ReiSequencedAssemblySubCategory.AssemblyPressing::new,
			() -> EmiSequencedAssemblySubCategory.AssemblyPressing::new
	);
	public static final CVW_SequencedAssemblySubCategoryType SPOUTING = new CVW_SequencedAssemblySubCategoryType(
			() -> JeiSequencedAssemblySubCategory.AssemblySpouting::new,
			() -> ReiSequencedAssemblySubCategory.AssemblySpouting::new,
			() -> EmiSequencedAssemblySubCategory.AssemblySpouting::new
	);
	public static final CVW_SequencedAssemblySubCategoryType DEPLOYING = new CVW_SequencedAssemblySubCategoryType(
			() -> JeiSequencedAssemblySubCategory.AssemblyDeploying::new,
			() -> ReiSequencedAssemblySubCategory.AssemblyDeploying::new,
			() -> EmiSequencedAssemblySubCategory.AssemblyDeploying::new
	);
	public static final CVW_SequencedAssemblySubCategoryType CUTTING = new CVW_SequencedAssemblySubCategoryType(
			() -> JeiSequencedAssemblySubCategory.AssemblyCutting::new,
			() -> ReiSequencedAssemblySubCategory.AssemblyCutting::new,
			() -> EmiSequencedAssemblySubCategory.AssemblyCutting::new
	);
}
