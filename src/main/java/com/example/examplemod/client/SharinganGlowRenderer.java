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
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            UchihaTM.LOGGER.info("[Sharingan] Player is null");
            clearTempGlow();
            return;
        }

        if (!ManaHUD.isSharinganActive()) {
            UchihaTM.LOGGER.info("[Sharingan] Sharingan not active");
            clearTempGlow();
            return;
        }

        if (mc.level == null) {
            UchihaTM.LOGGER.info("[Sharingan] Level is null");
            clearTempGlow();
            return;
        }

        UchihaTM.LOGGER.info("[Sharingan] Rendering glow, Sharingan ACTIVE");

        clearTempGlow();

        double radius = 10.0;
        double r2 = radius * radius;

        int count = 0;
        for (Entity e : mc.level.entitiesForRendering()) {
            if (!(e instanceof LivingEntity living)) continue;
            if (e == player) continue;

            double dx = e.getX() - player.getX();
            double dy = e.getY() - player.getY();
            double dz = e.getZ() - player.getZ();
            double dist = dx * dx + dy * dy + dz * dz;

            if (dist > r2) continue;

            count++;
            UchihaTM.LOGGER.info("[Sharingan] Found entity: {} at distance: {}", e.getClass().getSimpleName(), Math.sqrt(dist));

            if (!e.isCurrentlyGlowing()) {
                e.setGlowingTag(true);
                TEMP_GLOW.add(e);
                UchihaTM.LOGGER.info("[Sharingan] Set glow on: {}", e.getClass().getSimpleName());
            }
        }

        UchihaTM.LOGGER.info("[Sharingan] Total entities in radius: {}", count);
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
