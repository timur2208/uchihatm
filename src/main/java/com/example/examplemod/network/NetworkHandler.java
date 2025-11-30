package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistration;

/**
 * Обработчик сетевых пакетов
 */
public class NetworkHandler {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NetworkHandler::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistration registration = event.registrar(UchihaTM.MODID);

        registration.playToClient(
                ManaClientSyncPacket.class,
                (packet, buffer) -> packet.toBytes(buffer),
                handler -> new ManaClientSyncPacket(handler),
                handler -> handler::handle
        );
    }
}
