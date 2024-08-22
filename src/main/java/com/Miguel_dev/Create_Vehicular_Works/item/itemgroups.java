package com.Miguel_dev.Create_Vehicular_Works.item;

import com.Miguel_dev.Create_Vehicular_Works.CVW_main;
import com.Miguel_dev.Create_Vehicular_Works.block.functional_blocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class itemgroups {
    public static final CreativeModeTab CVW_Vehicle_parts = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
        new ResourceLocation(CVW_main.ID, "piston"),
        FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.cvw_vehicle_parts"))
            .icon(() -> new ItemStack(items.PISTON))
            .displayItems((displayContext, entries) -> {
                entries.accept(items.PISTON_Base);
                entries.accept(items.PISTON_Body);
                entries.accept(items.PISTON_head);
                entries.accept(items.PISTON);
            })
            .build()
    );

    public static final CreativeModeTab CVW_Blocks = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
        new ResourceLocation(CVW_main.ID, "vehicle_parts_depot"),
        FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.cvw_blocks"))
            .icon(() -> new ItemStack(functional_blocks.VEHICLE_PARTS_DEPOT_ITEM))
            .displayItems((displayContext, entries) -> {
                entries.accept(functional_blocks.VEHICLE_PARTS_DEPOT_ITEM);
            })
            .build()
    );

    public static void registergroups(){
        CVW_main.LOGGER.info("Adding creativemode groups for " + CVW_main.ID);
    }
}
