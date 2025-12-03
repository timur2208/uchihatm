package com.example.examplemod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class SharinganClientHandler {

    // вызывать при включении Шарингана
    public static void onSharinganEnabled() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        // Скорость I на большое время, без частиц
        MobEffectInstance speed = new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                20 * 60 * 60, // 1 час, по сути "навсегда", пока не выключишь
                0,            // уровень 0 = Speed I
                true,         // ambient (тихий)
                false,        // showParticles
                false         // showIcon
        );
        player.addEffect(speed);
    }

    // вызывать при выключении Шарингана
    public static void onSharinganDisabled() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        player.removeEffect(MobEffects.MOVEMENT_SPEED);
    }
}
