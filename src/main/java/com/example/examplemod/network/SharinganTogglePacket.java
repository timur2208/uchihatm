package com.example.examplemod.network;

import com.example.examplemod.mana.ManaManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SharinganTogglePacket {

    public SharinganTogglePacket() {}

    public static SharinganTogglePacket decode(FriendlyByteBuf buf) {
        return new SharinganTogglePacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            var uuid = player.getUUID();
            boolean now = !ManaManager.isSharinganActive(uuid);
            ManaManager.setSharingan(uuid, now);

            if (now) {
                // Включаем скорость I
                MobEffectInstance speed = new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        20 * 60 * 60,
                        0,
                        false,
                        false,
                        true
                );
                player.addEffect(speed);

                // Включаем GLOWING (встроенный эффект подсветки)
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
                // Выключаем оба эффекта
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                player.removeEffect(MobEffects.GLOWING);
            }
        });
        return true;
    }
}
