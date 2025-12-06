package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.client.ManaHUD;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SharinganSyncPacket implements CustomPacketPayload {

    public static final Type<SharinganSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(UchihaTM.MODID, "sharingan_sync"));

    public static final StreamCodec<ByteBuf, SharinganSyncPacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    msg -> msg.active,
                    SharinganSyncPacket::new
            );

    public final boolean active;

    public SharinganSyncPacket(boolean active) {
        this.active = active;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ManaHUD.setSharingan(active);
        });
    }
}
