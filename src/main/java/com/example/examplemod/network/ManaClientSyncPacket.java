package com.example.examplemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.client.ManaHUD;

/**
 * Пакет для синхронизации маны с клиентом
 */
public class ManaClientSyncPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UchihaTM.MODID, "mana_sync");

    public static final StreamCodec<FriendlyByteBuf, ManaClientSyncPacket> CODEC = StreamCodec.composite(
            FriendlyByteBuf::readInt,
            packet -> packet.currentMana,
            FriendlyByteBuf::readInt,
            packet -> packet.maxMana,
            ManaClientSyncPacket::new
    );

    private final int currentMana;
    private final int maxMana;

    public ManaClientSyncPacket(int currentMana, int maxMana) {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
