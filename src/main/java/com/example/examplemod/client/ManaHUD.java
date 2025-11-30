package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

/**
 * Клиентский HUD для отображения маны
 */
@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class ManaHUD {

    /**
     * Событие рендеринга GUI — рисуем ману сверху слева
     */
    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            return;
        }

        int currentMana = ManaHUDClientData.getCurrentMana();
        int maxMana = ManaHUDClientData.getMaxMana();
        String manaText = currentMana + "/" + maxMana;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        guiGraphics.drawString(minecraft.font, manaText, 10, 10, 0xFFFFFF, false);
    }
}
