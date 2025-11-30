package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.mana.ManaManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class ManaHUD {

    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;
    private static final int PADDING = 10;

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            return;
        }

        int current = ManaManager.getCurrentMana(player.getUUID());
        int max = ManaManager.getMaxMana(player.getUUID());

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        // Позиция: слева вверху, под координатами
        int x = PADDING;
        int y = PADDING + 50; // +50 если хотим ниже, можно изменить

        // Фон полоски (тёмно-серый)
        guiGraphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, 0xFF8B8B8B);

        // Чёрный border
        guiGraphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, 0xFF000000);

        // Вычисляем ширину заполненной части
        int filledWidth = (int) ((float) current / max * BAR_WIDTH);

        // Цвет полоски: градиент от зелёного к красному
        int color = interpolateColor(current, max);

        // Заполненная часть полоски
        guiGraphics.fill(x, y, x + filledWidth, y + BAR_HEIGHT, color);

        // Текст: "Мана: 50/100"
        String manaText = "Мана: " + current + "/" + max;
        guiGraphics.drawString(minecraft.font, manaText, x, y - 12, 0xFFFFFF, false);
    }

    /**
     * Интерполировать цвет между зелёным и красным в зависимости от процента маны
     */
    private static int interpolateColor(int current, int max) {
        float percent = (float) current / max;

        if (percent > 0.5f) {
            // От зелёного к жёлтому (когда ман больше 50%)
            int green = 255;
            int red = (int) (255 * (1 - (percent - 0.5f) * 2)); // От 255 к 0
            return 0xFF000000 | (red << 16) | (green << 8);
        } else {
            // От жёлтого к красному (когда ман меньше 50%)
            int red = 255;
            int green = (int) (255 * percent * 2); // От 0 к 255
            return 0xFF000000 | (red << 16) | (green << 8);
        }
    }
}
