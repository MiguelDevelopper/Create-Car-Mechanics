

package com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking;

import com.Miguel_dev.Create_Vehicular_Works.CVW_Blocks;
import com.Miguel_dev.Create_Vehicular_Works.CVW_RecipeTypes;
import com.Miguel_dev.Create_Vehicular_Works.CVW_main;
import com.Miguel_dev.Create_Vehicular_Works.compat.emi.EmiPartsMakerAssemblySubCategory;
import com.Miguel_dev.Create_Vehicular_Works.compat.jei.JeiPartsMakerAssemblySubCategory;
import com.Miguel_dev.Create_Vehicular_Works.compat.rei.ReiPartsMakerAssemblySubCategory;
import com.simibubi.create.compat.emi.EmiSequencedAssemblySubCategory;
import com.simibubi.create.compat.jei.category.BlockCuttingCategory;
import com.simibubi.create.compat.recipeViewerCommon.SequencedAssemblySubCategoryType;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Set;

public class PartsMakingRecipe extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {

    @SuppressWarnings("deprecation")
    public static RecipeSerializer<?> SERIALIZER = BuiltInRegistries.RECIPE_SERIALIZER.get(new ResourceLocation(CVW_main.ID, "partsmaking"));
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack output;

    protected PartsMakingRecipe(Ingredient ingredient, ItemStack output, ResourceLocation id) {
        super(new PartsMakingRecipeInfo(id, (SequencedAssemblyPartsMakingRecipeSerializer) SERIALIZER, CVW_RecipeTypes.PARTS_MAKING_TYPE.get()), new PartsMakerRecipeParams(id, ingredient, new ProcessingOutput(output, 1f)));
        this.output = output;
        this.id = id;
        this.ingredient = ingredient;
    }

    public static void register() {
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredient.test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        return this.output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height > 0;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return CVW_RecipeTypes.PARTS_MAKING_TYPE.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return this.output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

	@Override
    public Component getDescriptionForAssembly() {
        return Component.translatable("create_vehicular_works.recipe.partsmaking.secuence").withStyle(ChatFormatting.DARK_GREEN);
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> set) {
        set.add(CVW_Blocks.VEHICLE_PARTS_MAKER.get());
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {

    }

    @Override
    public SequencedAssemblySubCategoryType getJEISubCategory() {
        return new SequencedAssemblySubCategoryType(
                () -> JeiPartsMakerAssemblySubCategory::new,
                () -> ReiPartsMakerAssemblySubCategory::new,
                () -> EmiPartsMakerAssemblySubCategory::new);
    }
}
