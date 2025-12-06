package com.example.examplemod.network;

import com.example.examplemod.mana.ManaManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SharinganTogglePacket {

    public SharinganTogglePacket() {}

    public static SharinganTogglePacket decode(FriendlyByteBuf buf) {
        return new SharinganTogglePacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getPlayer();
            if (!(player instanceof ServerPlayer sp)) return;

            var uuid = sp.getUUID();
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
                sp.addEffect(speed);

                MobEffectInstance glowing = new MobEffectInstance(
                        MobEffects.GLOWING,
                        20 * 60 * 60,
                        0,
                        false,
                        false,
                        false
                );
                sp.addEffect(glowing);
            } else {
                sp.removeEffect(MobEffects.MOVEMENT_SPEED);
                sp.removeEffect(MobEffects.GLOWING);
            }
        });
    }
}
