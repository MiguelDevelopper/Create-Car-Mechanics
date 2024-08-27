package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class VehiclePartsMakerFilterSlot extends ValueBoxTransform {

	@Override
	public Vec3 getLocalOffset(BlockState state) {
		int offset = state.getValue(VehiclePartsMakerBlock.FLIPPED) ? -4 : 4;
		Vec3 x = VecHelper.voxelSpace(8, 12.5f, 8 + offset);
		Vec3 z = VecHelper.voxelSpace(8 + offset, 12.5f, 8);
		return state.getValue(VehiclePartsMakerBlock.AXIS_ALONG_FIRST_COORDINATE) ? z : x;
	}

	@Override
	public void rotate(BlockState state, PoseStack ms) {
		int yRot = (state.getValue(VehiclePartsMakerBlock.AXIS_ALONG_FIRST_COORDINATE) ? 90 : 0)
			+ (state.getValue(VehiclePartsMakerBlock.FLIPPED) ? 0 : 180);
		TransformStack.cast(ms)
			.rotateY(yRot)
			.rotateX(90);
	}

}
