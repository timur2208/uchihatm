package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.attachment.ManaAttachments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class ManaHUD {

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            return;
        }

        var manaData = player.getData(ManaAttachments.MANA);
        String manaText = manaData.getCurrentMana() + "/" + manaData.getMaxMana();

        GuiGraphics guiGraphics = event.getGuiGraphics();
        guiGraphics.drawString(minecraft.font, manaText, 10, 10, 0xFFFFFF, false);
    }
}
