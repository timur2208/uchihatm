package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.mana.ManaManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SharinganTogglePacket implements CustomPacketPayload {

    public static final Type<SharinganTogglePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(UchihaTM.MODID, "sharingan_toggle"));

    public static final StreamCodec<ByteBuf, SharinganTogglePacket> CODEC =
            new StreamCodec<ByteBuf, SharinganTogglePacket>() {
                public SharinganTogglePacket decode(ByteBuf buf) {
                    return new SharinganTogglePacket();
                }

                public void encode(ByteBuf buf, SharinganTogglePacket msg) {
                }
            };

    public SharinganTogglePacket() {}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player == null) return;

            var uuid = player.getUUID();
            boolean now = !ManaManager.isSharinganActive(uuid);
            ManaManager.setSharingan(uuid, now);

            if (now) {
                // Только скорость, без glow
                MobEffectInstance speed = new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED,
                        20 * 60 * 60,
                        0,
                        false,
                        false,
                        true
                );
                player.addEffect(speed);
            } else {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }
        });
    }
}
