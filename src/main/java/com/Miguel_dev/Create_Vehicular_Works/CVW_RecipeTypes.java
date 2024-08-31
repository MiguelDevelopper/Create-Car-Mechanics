package com.Miguel_dev.Create_Vehicular_Works;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

import com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking.PartsMakingRecipe;
import com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking.PartsMakingRecipeProcessingFactory;
import com.Miguel_dev.Create_Vehicular_Works.recipes.partsmaking.SequencedAssemblyPartsMakingRecipeSerializer;

public class CVW_RecipeTypes {
    public static final LazyRegistrar<RecipeSerializer<?>> SERIALIZERS =
            LazyRegistrar.create(Registries.RECIPE_SERIALIZER, CVW_main.ID);

    public static final LazyRegistrar<RecipeType<?>> RECIPE_TYPES = LazyRegistrar.create(Registries.RECIPE_TYPE, CVW_main.ID);

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

    public static final Supplier<RecipeType<PartsMakingRecipe>> PARTS_MAKING_TYPE = register("partsmaking");
    public static final RegistryObject<RecipeSerializer<?>> PARTS_MAKING_SERIALIZER = SERIALIZERS.register("partsmaking", () ->
            new SequencedAssemblyPartsMakingRecipeSerializer(new PartsMakingRecipeProcessingFactory()));

    public static void register() {
        RECIPE_TYPES.register();
        SERIALIZERS.register();
    }
}
