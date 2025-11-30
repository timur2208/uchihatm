package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.examplemod.network.ManaClientSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Обработчик тиков сервера для регенерации маны
 */
public class ManaTickEvent {

    private static final Map<UUID, String> playerLastDimension = new HashMap<>();
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        if (event.getServer() != null) {
            for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                UUID playerUUID = player.getUUID();

                String currentDimension = player.serverLevel().dimension().location().toString();
                String lastDimension = playerLastDimension.getOrDefault(playerUUID, "");

                if (!currentDimension.equals(lastDimension)) {
                    ManaManager.removePlayer(playerUUID);
                    ManaManager.getMana(playerUUID);
                    ManaPersistence.loadMana(playerUUID, player);
                    playerLastDimension.put(playerUUID, currentDimension);
                }

                ManaManager.getMana(playerUUID).updateRegen();

                if (tickCounter % 20 == 0) {
                    int current = ManaManager.getCurrentMana(playerUUID);
                    int max = ManaManager.getMaxMana(playerUUID);
                    ManaClientSyncPacket packet = new ManaClientSyncPacket(current, max);
                    PacketDistributor.sendToPlayer(packet, player);
                }
            }
            tickCounter++;
        }
    }

    public static void register() {
        NeoForge.EVENT_BUS.register(ManaTickEvent.class);
    }
}
