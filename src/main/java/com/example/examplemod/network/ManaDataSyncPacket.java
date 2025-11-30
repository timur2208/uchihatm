package com.example.examplemod.network;

import java.util.function.Supplier;

import com.example.examplemod.mana.ManaData;
import com.example.examplemod.mana.ManaEvents;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.codec.StreamCodec;
import net.neoforged.neoforge.network.NetworkEvent;
import io.netty.buffer.ByteBuf;

public record ManaDataSyncPacket(int currentMana, int maxMana) {
    public static final StreamCodec<ByteBuf, ManaDataSyncPacket> CODEC = StreamCodec.of(
            (packet, buf) -> {
                buf.writeInt(packet.currentMana);
                buf.writeInt(packet.maxMana);
            },
            buf -> new ManaDataSyncPacket(buf.readInt(), buf.readInt())
    );

    public static void handle(ManaDataSyncPacket packet, Supplier<IPayloadContext> context) {
        context.get().enqueueWork(() -> {
            // Обновляем клиентское значение
            ManaHUDClientData.setMana(packet.currentMana, packet.maxMana);
        });
    }
}
