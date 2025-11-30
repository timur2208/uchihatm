package com.example.examplemod.network;

import java.util.function.Supplier;

import com.example.examplemod.client.ManaHUDClientData;

import io.netty.buffer.ByteBuf;
import net.neoforged.neoforge.network.codec.StreamCodec;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
            ManaHUDClientData.setMana(packet.currentMana, packet.maxMana);
        }).exceptionally(ex -> {
            context.get().disconnect();
            return null;
        });
    }
}
