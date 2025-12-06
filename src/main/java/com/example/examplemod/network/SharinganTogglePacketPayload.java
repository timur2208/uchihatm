package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SharinganTogglePacketPayload implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SharinganTogglePacketPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(UchihaTM.MODID, "sharingan_toggle"));

    public static final StreamCodec<ByteBuf, SharinganTogglePacketPayload> CODEC =
            StreamCodec.unit(new SharinganTogglePacketPayload());

    public SharinganTogglePacketPayload() {}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        new SharinganTogglePacket().handle(context);
    }
}
