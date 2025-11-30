package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Обработчик событий маны
 */
public class ManaEvents {

    private static final Map<UUID, ManaData> playerManaMap = new HashMap<>();

    public static ManaData getManaData(ServerPlayer player) {
        return playerManaMap.computeIfAbsent(player.getUUID(), uuid -> new ManaData());
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaData mana = getManaData(player);
            syncMana(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaData mana = getManaData(player);
            mana.reset();
            syncMana(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            playerManaMap.remove(player.getUUID());
        }
    }

    public static void syncMana(ServerPlayer player) {
        ManaData mana = getManaData(player);
        // TODO: отправить пакет синхронизации
        // Пока пусто, добавим после регистрации сети
    }

    public static void register() {
        NeoForge.EVENT_BUS.register(ManaEvents.class);
    }
}
