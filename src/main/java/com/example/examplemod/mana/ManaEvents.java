package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Обработчик событий маны
 */
public class ManaEvents {

    // Хранилище маны для каждого игрока по UUID
    private static final Map<UUID, ManaData> playerManaMap = new HashMap<>();

    /**
     * Получить или создать ManaData для игрока
     */
    public static ManaData getManaData(ServerPlayer player) {
        return playerManaMap.computeIfAbsent(player.getUUID(), uuid -> new ManaData());
    }

    /**
     * Событие при респауне игрока — загружаем ману
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaData mana = getManaData(player);
        }
    }

    /**
     * Событие при смерти игрока — сбрасываем ману в 0
     */
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaData mana = getManaData(player);
            mana.reset();
        }
    }

    /**
     * Событие при выходе игрока — удаляем его ману из памяти
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            playerManaMap.remove(player.getUUID());
        }
    }

    // Регистрация событий
    public static void register() {
        NeoForge.EVENT_BUS.register(ManaEvents.class);
    }
}
