package com.Miguel_dev.Create_Vehicular_Works;

import com.simibubi.create.content.kinetics.base.CutoutRotatingInstance;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.Miguel_dev.Create_Vehicular_Works.CVW_main.REGISTRATE;;


public class CVW_BlockEntityTypes {
    public static final BlockEntityEntry<VehiclePartsMakerEntity> VEHICLE_PARTS_MAKER = REGISTRATE
        .blockEntity("vehicle_parts_maker", VehiclePartsMakerEntity::new)
        .instance(() -> ShaftInstance::new)
            .validBlocks(CVW_Blocks.VEHICLE_PARTS_MAKER)
            .renderer(() -> EnergiserRenderer::new)
            .register();

}
