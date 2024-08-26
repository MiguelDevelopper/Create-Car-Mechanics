package com.Miguel_dev.Create_Vehicular_Works;

import com.simibubi.create.foundation.data.CreateRegistrate;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CVW_main implements ModInitializer {
	public static final String ID = "create_vehicular_works";
	public static final String NAME = "Create Vehicular Works";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);
	public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ID,"tab"));


	@Override
	public void onInitialize() {
		CVW_CreativeTabs.register();
		CVW_Blocks.register();
		CVW_Items.register();
		CVW_BlockEntityTypes.register();
		CVW_RecipeTypes.register();
		
		REGISTRATE.register();
	}

	public static ResourceLocation resourceLocation(String path) {
		return new ResourceLocation(ID, path);
	}
}
