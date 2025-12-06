package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistration;

public class ModNetwork {

    public static void register(PayloadRegistration registrar) {
        registrar.playToServer(
                SharinganTogglePacketPayload.TYPE,
                SharinganTogglePacketPayload.CODEC,
                (packet, context) -> packet.handle(context)
        );
    }
}
