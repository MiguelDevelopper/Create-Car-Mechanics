package com.Miguel_dev.Create_Vehicular_Works;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.ponder.FabricPonderProcessing;

import java.util.Random;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CVW_main implements ModInitializer {
	public static final String ID = "create_vehicular_works";
	public static final String NAME = "Create Vehicular Works";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	public static final Random RANDOM = new Random();

	@Override
	public void onInitialize() {
		CVW_Blocks.register();
		CVW_BlockEntityTypes.register();
		CVW_Items.register();
		CVW_RecipeTypes.register();
		CVW_SoundEvents.prepare();
		CVW_CreativeTabs.register();
		
		REGISTRATE.register();
	}

	public static ResourceLocation resourceLocation(String path) {
		return new ResourceLocation(ID, path);
	}
}
