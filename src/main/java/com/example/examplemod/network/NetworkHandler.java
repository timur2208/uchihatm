package com.example.examplemod.network;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistration;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.client.ManaHUD;

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
                ManaClientSyncPacket.CODEC,
                NetworkHandler::handleClientPacket
        );
    }

    @EventBusSubscriber(modid = UchihaTM.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    private static class ClientPacketHandler {
        static void handleClientPacket(ManaClientSyncPacket packet, IPayloadContext context) {
            context.enqueueWork(() -> {
                ManaHUD.clientMana = packet.currentMana;
                ManaHUD.clientMaxMana = packet.maxMana;
            });
        }
    }

    private static void handleClientPacket(ManaClientSyncPacket packet, IPayloadContext context) {
        Minecraft.getInstance().execute(() -> {
            ManaHUD.clientMana = packet.currentMana;
            ManaHUD.clientMaxMana = packet.maxMana;
        });
    }
}
