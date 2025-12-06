package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistration;

public class ModNetwork {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistration registrar = event.registrar(UchihaTM.MODID);

        registrar.playToServer(
                SharinganTogglePacketPayload.TYPE,
                SharinganTogglePacketPayload.CODEC,
                (packet, context) -> packet.handle(context)
        );
    }
}
