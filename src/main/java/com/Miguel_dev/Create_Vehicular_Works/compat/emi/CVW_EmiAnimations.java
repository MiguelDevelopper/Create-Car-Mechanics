package com.Miguel_dev.Create_Vehicular_Works.compat.emi;

import com.Miguel_dev.Create_Vehicular_Works.CVW_Blocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.foundation.gui.CustomLightingSettings;
import com.simibubi.create.foundation.gui.ILightingSettings;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

public class CVW_EmiAnimations {
    public static final ILightingSettings DEFAULT_LIGHTING = CustomLightingSettings.builder()
            .firstLightRotation(12.5f, 45.0f)
            .secondLightRotation(-20.0f, 50.0f)
            .build();


    public static void addRoller(WidgetHolder widgets, int x, int y, int offset) {
        widgets.addDrawable(x, y, 0, 0, (graphics, mouseX, mouseY, delta) -> {
            renderRoller(graphics, offset);
        });
    }

    public static void renderRoller(GuiGraphics graphics, int offset) {
        PoseStack matrices = graphics.pose();
        matrices.translate(-5, offset + 16, 200);
        matrices.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-15.5f));
        matrices.mulPose(com.mojang.math.Axis.YP.rotationDegrees(22.5f));
        int scale = 25;

        CreateEmiAnimations.blockElement(CVW_Blocks.VEHICLE_PARTS_MAKER.getDefaultState())
                .rotateBlock(0, 180, 0)
                .scale(scale)
                .render(graphics);

        CreateEmiAnimations.blockElement(CreateEmiAnimations.shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, CreateEmiAnimations.getCurrentAngle())
                .scale(scale)
                .atLocal(0, 0, 0)
                .render(graphics);
    }
	
}
