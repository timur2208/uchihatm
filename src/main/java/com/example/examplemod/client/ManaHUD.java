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
@EventBusSubscriber(modid = UchihaTM.MODID, bus = EventBusSubscriber.Bus.NEOFORGE, value = Dist.CLIENT)
public class ManaHUD {

    private static int clientMana = 50;
    private static int clientMaxMana = 100;

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

        String manaText = clientMana + "/" + clientMaxMana;
        GuiGraphics guiGraphics = event.getGuiGraphics();

        // Рисуем текст в левом верхнем углу (позиция 10, 10) белым цветом (0xFFFFFF)
        guiGraphics.drawString(minecraft.font, manaText, 10, 10, 0xFFFFFF, false);
    }
}
