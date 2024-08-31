package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import com.Miguel_dev.Create_Vehicular_Works.CVW_main;
import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.saw.SawBlock;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public class VehiclePartsMakerRenderer extends KineticBlockEntityRenderer<VehiclePartsMakerBlockEntity> {

	public VehiclePartsMakerRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	protected BlockState getRenderedBlockState(VehiclePartsMakerBlockEntity te) {
		return shaft(getRotationAxisOf(te));
	}
	
	@Override
	protected void renderSafe(VehiclePartsMakerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
			int light, int overlay) {
		renderItems(be, partialTicks, ms, buffer, light, overlay);
		super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
		if(Backend.canUseInstancing(be.getLevel())) return;
		BlockState blockState = be.getBlockState();
		BlockPos pos = be.getBlockPos();
		
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
		
		int packedLightmapCoords = LevelRenderer.getLightColor(be.getLevel(), pos);
		// SuperByteBuffer shaft = AllBlockPartials.SHAFT_HALF.renderOn(blockState);
		SuperByteBuffer shaft =  CachedBufferer.partial(AllPartialModels.SHAFT_HALF, blockState);
		Axis axis = getRotationAxisOf(be);
		
		shaft
			.rotateCentered(Direction.UP, axis == Axis.Z ? 0 : 90*(float)Math.PI/180f)
			.translate(0, 4f/16f, 0)
			.rotateCentered(Direction.NORTH, getAngleForTe(be, pos, axis))
			.light(packedLightmapCoords)
			.renderInto(ms, vb);
		
		shaft
			.rotateCentered(Direction.UP, axis == Axis.Z ? 180*(float)Math.PI/180f : 270*(float)Math.PI/180f)
			.translate(0, 4f/16f, 0)
			.rotateCentered(Direction.NORTH, -getAngleForTe(be, pos, axis))
			.light(packedLightmapCoords)
			.renderInto(ms, vb);
	}

	protected void renderItems(VehiclePartsMakerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		if (!be.inventory.isEmpty()) {
			boolean alongZ = be.getBlockState().getValue(VehiclePartsMakerBlock.HORIZONTAL_FACING) == Direction.SOUTH || be.getBlockState().getValue(VehiclePartsMakerBlock.HORIZONTAL_FACING) == Direction.NORTH;
			ms.pushPose();

			boolean moving = be.inventory.recipeDuration != 0;
			float offset = moving ? (float) (be.inventory.remainingTime) / be.inventory.recipeDuration : 0;
			float processingSpeed = Mth.clamp(Math.abs(be.getSpeed()) / 32, 1, 128);
			if (moving) {
				offset = Mth
					.clamp(offset + ((-partialTicks + .5f) * processingSpeed) / be.inventory.recipeDuration, 0.125f, 1f);
				if (!be.inventory.appliedRecipe)
					offset += 1;
				offset /= 2;
			}

			if (be.getSpeed() == 0)
				offset = .5f;
			if (be.getSpeed() < 0 == alongZ) // Changed from ^ to ==
				offset = 1 - offset;
			
			for (int i = 0; i < be.inventory.getSlotCount(); i++) {
				ItemStack stack = be.inventory.getStackInSlot(i);
				if (stack.isEmpty())
					continue;

				ItemRenderer itemRenderer = Minecraft.getInstance()
					.getItemRenderer();
				BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
				boolean blockItem = modelWithOverrides.isGui3d();

				ms.translate(alongZ ? offset : .5, blockItem ? .925f : 13f / 16f, alongZ ? .5 : offset);

				ms.scale(.5f, .5f, .5f);
				if (alongZ)
					ms.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90));
				ms.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90));
				itemRenderer.render(stack, ItemDisplayContext.FIXED, false, ms, buffer, light, overlay, modelWithOverrides);
				break;
			}

			ms.popPose();
		}
	}

}