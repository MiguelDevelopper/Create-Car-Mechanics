package com.Miguel_dev.Create_Vehicular_Works;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerBlock;
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
		.addLayer(() -> RenderType::cutoutMipped)
		.properties(p -> p.mapColor(MapColor.PODZOL))
		.transform(axeOrPickaxe())
		//.blockstate(new VehiclePartsMakerGenerator()::generate)
		.transform(BlockStressDefaults.setImpact(4.0))
		.addLayer(() -> RenderType::cutoutMipped)
		.item()
		.tag(AllItemTags.CONTRAPTION_CONTROLLED.tag)
		.transform(customItemModel())
		.register();
	
	public static void register() { 
		CVW_main.LOGGER.info("Registering blocks for " + CVW_main.ID);
	 }
}
