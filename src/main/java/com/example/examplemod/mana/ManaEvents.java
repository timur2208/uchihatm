package com.example.examplemod.mana;

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

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerUUID = player.getUUID();

            // ВАЖНО: Очищаем ман из памяти ПРИ ВХОДЕ
            // Это гарантирует что файл загрузится заново
            ManaManager.removePlayer(playerUUID);

            // Инициализируем ману
            ManaManager.getMana(playerUUID);

            // Загружаем из файла ЭТОГО МИРА если существует
            ManaPersistence.loadMana(playerUUID, player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerUUID = player.getUUID();
            ManaManager.resetMana(playerUUID);
            // Сохраняем при смерти в этот мир
            ManaPersistence.saveMana(playerUUID, ManaManager.getMana(playerUUID), player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerUUID = player.getUUID();
            // Сохраняем перед выходом в этот мир
            ManaPersistence.saveMana(playerUUID, ManaManager.getMana(playerUUID), player);
            // Удаляем из памяти
            ManaManager.removePlayer(playerUUID);
        }
    }

    public static void register() {
        NeoForge.EVENT_BUS.register(ManaEvents.class);
        ManaTickEvent.register();
    }
}
