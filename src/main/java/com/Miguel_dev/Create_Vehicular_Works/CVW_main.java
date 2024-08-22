package com.Miguel_dev.Create_Vehicular_Works;

import com.Miguel_dev.Create_Vehicular_Works.block.functional_blocks;
import com.Miguel_dev.Create_Vehicular_Works.item.itemgroups;
import com.Miguel_dev.Create_Vehicular_Works.item.items;
import com.simibubi.create.Create;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CVW_main implements ModInitializer {
	public static final String ID = "create_vehicular_works";
	public static final String NAME = "Create Vehicular Works";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {
		itemgroups.registergroups();
		items.registerItems();
		functional_blocks.registerBlocks();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
