package com.example.examplemod.network;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ManaDataPayload implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("uchihatm", "mana_sync");

    public static final StreamCodec<ByteBuf, ManaDataPayload> CODEC = StreamCodec.composite(
            FriendlyByteBuf::writeInt,
            payload -> payload.currentMana,
            FriendlyByteBuf::readInt,
            FriendlyByteBuf::writeInt,
            payload -> payload.maxMana,
            FriendlyByteBuf::readInt,
            (current, max) -> new ManaDataPayload(current, max)
    );

    private final int currentMana;
    private final int maxMana;

    public ManaDataPayload(int currentMana, int maxMana) {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }
}
