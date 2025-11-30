package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Клиентский HUD для отображения маны
 */
@EventBusSubscriber(modid = UchihaTM.MODID, bus = EventBusSubscriber.Bus.NEOFORGE, value = Dist.CLIENT)
public class ManaHUD {

    // Временное хранилище текущей маны (будет синхронизироваться с сервера позже)
    private static int clientMana = 0;
    private static int clientMaxMana = 100;

    /**
     * Событие рендеринга GUI — рисуем ману сверху слева
     */
    @SubscribeEvent
    public static void onRenderGui(ScreenEvent.Init.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null || minecraft.screen != null) {
            return; // Если меню открыто или нет игрока, не рисуем
        }

        // Временно используем константные значения (позже будет синхронизация)
        clientMana = 50; // TODO: получать от сервера
        clientMaxMana = 100;

        String manaText = clientMana + "/" + clientMaxMana;

        // GuiGraphics для рендеринга текста
        GuiGraphics guiGraphics = event.getGuiGraphics();

        // Рисуем текст в левом верхнем углу (позиция 10, 10) белым цветом (0xFFFFFF)
        guiGraphics.drawString(minecraft.font, manaText, 10, 10, 0xFFFFFF, false);
    }
}
