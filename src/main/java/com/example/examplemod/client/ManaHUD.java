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

    private static final int BAR_WIDTH = 6;
    private static final int BAR_HEIGHT = 60;
    private static final int PADDING = 10;

    public static int clientMana = 50;
    public static int clientMaxMana = 100;

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) {
            return;
        }

        GuiGraphics guiGraphics = event.getGuiGraphics();

        int x = PADDING;
        int y = PADDING;

        guiGraphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, 0xFF000000);
        guiGraphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, 0xFF4B4B4B);

        int filledHeight = (int) ((float) clientMana / clientMaxMana * BAR_HEIGHT);
        int color = interpolateColor(clientMana, clientMaxMana);

        int fillStartY = y + BAR_HEIGHT - filledHeight;
        guiGraphics.fill(x, fillStartY, x + BAR_WIDTH, y + BAR_HEIGHT, color);

        String manaText = "М: " + clientMana + "/" + clientMaxMana;
        guiGraphics.drawString(minecraft.font, manaText, x + BAR_WIDTH + 5, y, 0xFFFFFF, false);
    }

    private static int interpolateColor(int current, int max) {
        float percent = (float) current / max;

        if (percent > 0.5f) {
            int green = 255;
            int red = (int) (255 * (1 - (percent - 0.5f) * 2));
            return 0xFF000000 | (red << 16) | (green << 8);
        } else {
            int red = 255;
            int green = (int) (255 * percent * 2);
            return 0xFF000000 | (red << 16) | (green << 8);
        }
    }
}
