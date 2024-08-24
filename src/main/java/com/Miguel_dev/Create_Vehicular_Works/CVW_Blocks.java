package com.Miguel_dev.Create_Vehicular_Works;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;

import com.Miguel_dev.Create_Vehicular_Works.content.VehiclePartsMaker.VehiclePartsMakerBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CVW_Blocks {

	public static final BlockEntry<Block> VEHICLE_PARTS_MAKER =
            REGISTRATE.block("vehicle_parts_maker", VehiclePartsMakerBlock::newVPM)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .transform(BlockStressDefaults.setImpact(4.0))
                    .item(AssemblyOperatorBlockItem::new)
                    .build()
                    .register();

	public static void register() { 
		CVW_main.LOGGER.info("Registering blocks for " + CVW_main.ID);
	 }
}
