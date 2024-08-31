package com.Miguel_dev.Create_Vehicular_Works;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerBlock;
import com.simibubi.create.AllTags.AllBlockTags;
//import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerGenerator;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

public class CVW_Blocks {

	public static final BlockEntry<VehiclePartsMakerBlock> VEHICLE_PARTS_MAKER = REGISTRATE.block("vehicle_parts_maker", VehiclePartsMakerBlock::new)
		.initialProperties(SharedProperties::stone)
		.transform(BlockStressDefaults.setImpact(8/*Config.ROLLING_MILL_STRESS.get()*/))
		.tag(AllBlockTags.SAFE_NBT.tag) //Dono what this tag means (contraption safe?).
		.item()
		.transform(customItemModel())
		.register();


	public static void register() { 
		CVW_main.LOGGER.info("Registering blocks for " + CVW_main.ID);
	 }
}
