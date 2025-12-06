package com.example.examplemod.network;

import com.example.examplemod.mana.ManaManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SharinganTogglePacket {

    public SharinganTogglePacket() {}

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player == null) return;

            var uuid = player.getUUID();
            boolean now = !ManaManager.isSharinganActive(uuid);
            ManaManager.setSharingan(uuid, now);

            if (now) {
                MobEffectInstance speed = new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        20 * 60 * 60,
                        0,
                        false,
                        false,
                        true
                );
                player.addEffect(speed);

                MobEffectInstance glowing = new MobEffectInstance(
                        MobEffects.GLOWING,
                        20 * 60 * 60,
                        0,
                        false,
                        false,
                        false
                );
                player.addEffect(glowing);
            } else {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                player.removeEffect(MobEffects.GLOWING);
            }
        });
    }
}
