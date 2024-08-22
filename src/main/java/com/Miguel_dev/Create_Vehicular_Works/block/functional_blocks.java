package com.Miguel_dev.Create_Vehicular_Works.block;

import com.Miguel_dev.Create_Vehicular_Works.CVW_main;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class functional_blocks {
    // spesific block registers
    public static final vehicle_parts_Depot VEHICLE_PARTS_DEPOT = registerVPD("vehicle_parts_depot", new vehicle_parts_Depot(BlockBehaviour.Properties.of().strength(2.0f)));

    // item registers
    public static final Item VEHICLE_PARTS_DEPOT_ITEM = registerBlockItem("vehicle_parts_depot", VEHICLE_PARTS_DEPOT);

    //general block register method
    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(CVW_main.ID, name), block);
    }

    // general item register method
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CVW_main.ID, name), 
            new BlockItem(block, new FabricItemSettings()));
    }

    //special blocks register methods
    private static vehicle_parts_Depot registerVPD(String name, vehicle_parts_Depot block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(CVW_main.ID, name), block);
    }

    // Método para registrar todos los bloques
    public static void registerBlocks() {
        CVW_main.LOGGER.info("Registering blocks for " + CVW_main.ID);
    }
}
