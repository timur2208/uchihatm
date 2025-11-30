package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Обработчик событий маны
 */
@EventBusSubscriber(modid = "uchihatm", bus = EventBusSubscriber.Bus.NEOFORGE)
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
            // Получаем или создаём ManaData
            ManaData mana = getManaData(player);
            // Можно добавить логирование
            // UchihaTM.LOGGER.info("Игрок {} вошёл, мана: {}/{}", player.getName().getString(), mana.getCurrentMana(), mana.getMaxMana());
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
     * Событие при выходе игрока — удаляем его ману из памяти (опционально)
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            playerManaMap.remove(player.getUUID());
        }
    }
}
