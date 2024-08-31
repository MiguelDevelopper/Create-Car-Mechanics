package com.Miguel_dev.Create_Vehicular_Works;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class CVW_CreativeTabs {
    public static final CreativeModeTab CVW_MainTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
    CVW_main.resourceLocation( "cvw_items"),
        FabricItemGroup.builder()
            .title(Component.translatable("tab.create_vehicular_works.MainTab"))
            .icon(() -> CVW_Blocks.VEHICLE_PARTS_MAKER.asStack())
            .displayItems((displayContext, entries) -> {
                entries.accept(CVW_Blocks.VEHICLE_PARTS_MAKER);
                entries.accept(CVW_Items.PISTON_BASE_TRIM);
                entries.accept(CVW_Items.PISTON_HEAD_TRIM);
                entries.accept(CVW_Items.PISTON_BASE);
                entries.accept(CVW_Items.PISTON_BODY);
                entries.accept(CVW_Items.PISTON_HEAD);
                entries.accept(CVW_Items.PISTON);
            })
            .build()
    );

    public static void register(){
        CVW_main.LOGGER.info("creating tabs for " + CVW_main.ID);
    }
}
