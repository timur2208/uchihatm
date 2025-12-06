package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.mana.ManaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class ManaHUD {

    private static final int BAR_WIDTH = 10;
    private static final int BAR_HEIGHT = 80;
    private static final int PADDING_X = 8;
    private static final int PADDING_Y = 8;

    private static boolean sharinganActive = false;

    public static void toggleSharingan() {
        sharinganActive = !sharinganActive;
    }

    public static void setSharingan(boolean active) {
        sharinganActive = active;
    }

    public static boolean isSharinganActive() {
        return sharinganActive;
    }

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            return;
        }

        int current = ManaManager.getCurrentMana(player.getUUID());
        int max = ManaManager.getMaxMana(player.getUUID());

        if (current == 0 && max == 0) {
            return;
        }

        GuiGraphics gg = event.getGuiGraphics();
        int x = PADDING_X;
        int y = PADDING_Y;

        drawBackground(gg, x, y);
        drawManaBar(gg, x, y, current, max);

        int textColor = sharinganActive ? 0xFFFF4040 : 0xFFEFEFEF;
        String text = "Мана: " + current + "/" + max;
        gg.drawString(mc.font, text, x + BAR_WIDTH + 6, y + 2, textColor, false);
    }

    private static void drawBackground(GuiGraphics gg, int x, int y) {
        int x0 = x - 2;
        int y0 = y - 2;
        int x1 = x + BAR_WIDTH + 2;
        int y1 = y + BAR_HEIGHT + 2;

        gg.fill(x0, y0, x1, y1, 0xAA000000);
        gg.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, 0xFF151515);

        int borderColor = 0xFF551111;
        gg.fill(x0, y0, x1, y0 + 1, borderColor);
        gg.fill(x0, y1 - 1, x1, y1, borderColor);
        gg.fill(x0, y0, x0 + 1, y1, borderColor);
        gg.fill(x1 - 1, y0, x1, y1, borderColor);
    }

    private static void drawManaBar(GuiGraphics gg, int x, int y, int current, int max) {
        if (max <= 0) {
            return;
        }

        float perc = Mth.clamp((float) current / max, 0f, 1f);
        int filledHeight = (int) (BAR_HEIGHT * perc);
        int topFilled = y + BAR_HEIGHT - filledHeight;

        for (int i = 0; i < filledHeight; i++) {
            float t = (float) i / Math.max(1, filledHeight - 1);
            int color = interpolateBordoToRed(t);
            int yLine = topFilled + i;
            gg.fill(x + 1, yLine, x + BAR_WIDTH - 1, yLine + 1, color);
        }

        if (filledHeight > 0) {
            int glossTop = topFilled;
            int glossBottom = y + BAR_HEIGHT;
            gg.fill(x + 1, glossTop, x + 2, glossBottom, 0x40FFFFFF);
        }
    }

    private static int interpolateBordoToRed(float t) {
        t = Mth.clamp(t, 0f, 1f);

        int startR = 90,  startG = 0,  startB = 10;
        int midR   = 160, midG   = 10, midB   = 20;
        int endR   = 255, endG   = 40, endB   = 40;

        int r, g, b;
        if (t < 0.5f) {
            float k = t / 0.5f;
            r = (int) (startR + (midR - startR) * k);
            g = (int) (startG + (midG - startG) * k);
            b = (int) (startB + (midB - startB) * k);
        } else {
            float k = (t - 0.5f) / 0.5f;
            r = (int) (midR + (endR - midR) * k);
            g = (int) (midG + (endG - midG) * k);
            b = (int) (midB + (endB - midB) * k);
        }

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
