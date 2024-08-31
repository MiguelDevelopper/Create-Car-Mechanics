package com.Miguel_dev.Create_Vehicular_Works.compat.jei;

import javax.annotation.Nonnull;

import com.Miguel_dev.Create_Vehicular_Works.CVW_Blocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

public class AnimatedPartsMaker extends AnimatedKinetics {

	
	@Override
	public void draw(@Nonnull GuiGraphics graphics, int xOffset, int yOffset) {
		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		matrixStack.translate(0, 0, 200);
		matrixStack.translate(2, 22, 0);
		matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f + 90));
		int scale = 25;

		GuiGameElement.of(shaft(Direction.Axis.X))
			.rotateBlock(-getCurrentAngle(), 0, 0)
			.scale(scale)
			.render(graphics);

		GuiGameElement.of(CVW_Blocks.VEHICLE_PARTS_MAKER.getDefaultState())
			.rotateBlock(0, 90, 0)
			.scale(scale)
			.render(graphics);
		matrixStack.popPose();
	}

}