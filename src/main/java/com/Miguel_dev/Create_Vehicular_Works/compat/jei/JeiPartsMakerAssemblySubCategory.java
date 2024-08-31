package com.Miguel_dev.Create_Vehicular_Works.compat.jei;

import com.simibubi.create.compat.jei.category.sequencedAssembly.JeiSequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class JeiPartsMakerAssemblySubCategory extends JeiSequencedAssemblySubCategory {

    AnimatedPartsMaker partsmaker;

    public JeiPartsMakerAssemblySubCategory() {
        super(20);
        partsmaker = new AnimatedPartsMaker();
    }

    @Override
    public void draw(SequencedRecipe<?> sequencedRecipe, GuiGraphics ms, double mouseX, double mouseY, int index) {
        ms.pose().pushPose();
        ms.pose().translate(0, 51.5f, 0);
        ms.pose().scale(.6f, .6f, .6f);
        partsmaker.draw(ms, getWidth() / 2, 30);
        ms.pose().popPose();
    }
}