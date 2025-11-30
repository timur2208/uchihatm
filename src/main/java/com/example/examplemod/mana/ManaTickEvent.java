package com.example.examplemod.mana;

import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Обработчик тиков сервера для регенерации маны
 */
public class ManaTickEvent {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        if (event.getServer() != null) {
            for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                UUID playerUUID = player.getUUID();
                ManaManager.getMana(playerUUID).updateRegen();
            }
        }
    }

    public static void register() {
        NeoForge.EVENT_BUS.register(ManaTickEvent.class);
    }
}
