package com.Miguel_dev.Create_Vehicular_Works;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;

import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerBlockEntity;
import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerInstance;
import com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker.VehiclePartsMakerRenderer;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CVW_BlockEntityTypes {
	
	public static final BlockEntityEntry<VehiclePartsMakerBlockEntity> VEHICLE_PARTS_MAKER = REGISTRATE
			.blockEntity("vehicle_parts_maker", VehiclePartsMakerBlockEntity::new)
			.instance(() -> VehiclePartsMakerInstance::new)
			.validBlocks(CVW_Blocks.VEHICLE_PARTS_MAKER)
			.renderer(() -> VehiclePartsMakerRenderer::new)
			.register();
	
	
	public static void register() {
        CVW_main.LOGGER.info("registering block entities for " + CVW_main.NAME);
	}
}