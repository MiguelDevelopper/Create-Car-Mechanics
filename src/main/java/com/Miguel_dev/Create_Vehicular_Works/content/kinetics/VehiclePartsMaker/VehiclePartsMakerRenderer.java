package com.Miguel_dev.Create_Vehicular_Works.content.kinetics.VehiclePartsMaker;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class VehiclePartsMakerRenderer extends SafeBlockEntityRenderer<VehiclePartsMakerBlockEntity> {

	public VehiclePartsMakerRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(VehiclePartsMakerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light,
		int overlay) {
		renderItems(be, partialTicks, ms, buffer, light, overlay);
		FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);

		if (Backend.canUseInstancing(be.getLevel()))
			return;

		renderShaft(be, ms, buffer, light, overlay);
	}


	protected void renderShaft(VehiclePartsMakerBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, null, ms,
			buffer.getBuffer(RenderType.solid()), light);
	}

	protected void renderItems(VehiclePartsMakerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
		int light, int overlay) {
		boolean processingMode = be.getBlockState()
			.getValue(VehiclePartsMakerBlock.FACING) == Direction.UP;
		if (processingMode && !be.inventory.isEmpty()) {
			boolean alongZ = !be.getBlockState()
				.getValue(VehiclePartsMakerBlock.AXIS_ALONG_FIRST_COORDINATE);
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
			if (be.getSpeed() < 0 ^ alongZ)
				offset = 1 - offset;
			
			offset = (-offset + 1);

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
					ms.mulPose(Axis.YP.rotationDegrees(90));
				ms.mulPose(Axis.XP.rotationDegrees(90));
				itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffer, be.getLevel(), 0);
				break;
			}

			ms.popPose();
		}
	}

	

	protected BlockState getRenderedBlockState(KineticBlockEntity be) {
		return KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be));
	}

	public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
		ContraptionMatrices matrices, MultiBufferSource buffer) {

	    Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(VehiclePartsMakerBlock.FACING)
			.getNormal());
		facingVec = context.rotation.apply(facingVec);
		
	}

}