package com.Miguel_dev.Create_Vehicular_Works.item;

import com.Miguel_dev.Create_Vehicular_Works.CVW_main;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class items {
    public static final Item PISTON_Base = registerItem("piston_base", new Item(new FabricItemSettings()));
    public static final Item PISTON_Body = registerItem("piston_body", new Item(new FabricItemSettings()));
    public static final Item PISTON_head = registerItem("piston_head", new Item(new FabricItemSettings()));
    public static final Item PISTON = registerItem("piston", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CVW_main.ID, name), item);
    }
    public static void registerItems(){
        CVW_main.LOGGER.info("Registering items for " + CVW_main.ID);
    }

}
