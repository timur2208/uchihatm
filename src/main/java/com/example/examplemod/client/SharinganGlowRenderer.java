package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class SharinganGlowRenderer {

    private static final List<Entity> TEMP_GLOW = new ArrayList<>();

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || !ManaHUD.isSharinganActive() || mc.level == null) {
            clearTempGlow();
            return;
        }

        clearTempGlow();

        double radius = 10.0;
        double r2 = radius * radius;

        for (Entity e : mc.level.entitiesForRendering()) {
            if (!(e instanceof LivingEntity living)) continue;
            if (e == player) continue;

            double dx = e.getX() - player.getX();
            double dy = e.getY() - player.getY();
            double dz = e.getZ() - player.getZ();
            if (dx * dx + dy * dy + dz * dz > r2) continue;

            if (!e.isCurrentlyGlowing()) {
                e.setGlowingTag(true);
                TEMP_GLOW.add(e);
            }
        }
    }

    private static void clearTempGlow() {
        if (TEMP_GLOW.isEmpty()) return;
        for (Entity e : TEMP_GLOW) {
            if (e != null && e.isCurrentlyGlowing()) {
                e.setGlowingTag(false);
            }
        }
        TEMP_GLOW.clear();
    }
}
