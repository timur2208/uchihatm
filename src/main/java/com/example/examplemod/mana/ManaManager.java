package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Менеджер маны игроков
 */
public class ManaManager {

    private static final Map<UUID, ManaData> playerMana = new HashMap<>();
    private static final Map<UUID, Boolean> playerInitialized = new HashMap<>();

    /**
     * Получить ману игрока (или создать 0/0 если нет)
     */
    public static ManaData getMana(UUID playerUUID) {
        return playerMana.computeIfAbsent(playerUUID, k -> new ManaData(0, 0));
    }

    /**
     * Проверить инициализирован ли игрок
     */
    public static boolean isInitialized(UUID playerUUID) {
        return playerInitialized.getOrDefault(playerUUID, false);
    }

    /**
     * Инициализировать игрока
     */
    public static void initializePlayer(UUID playerUUID) {
        playerInitialized.put(playerUUID, true);
        playerMana.put(playerUUID, new ManaData(50, 100));
    }

    /**
     * Получить текущую ман
     */
    public static int getCurrentMana(UUID playerUUID) {
        return getMana(playerUUID).getCurrentMana();
    }

    /**
     * Получить максимальную ман
     */
    public static int getMaxMana(UUID playerUUID) {
        return getMana(playerUUID).getMaxMana();
    }

    /**
     * Добавить ман (если инициализирован)
     */
    public static void addMana(UUID playerUUID, int amount) {
        if (isInitialized(playerUUID)) {
            getMana(playerUUID).addMana(amount);
        }
    }

    /**
     * Сбросить ман при смерти
     */
    public static void resetMana(UUID playerUUID) {
        if (isInitialized(playerUUID)) {
            getMana(playerUUID).setCurrentMana(0);
        }
    }

    /**
     * Удалить игрока из памяти
     */
    public static void removePlayer(UUID playerUUID) {
        playerMana.remove(playerUUID);
        playerInitialized.remove(playerUUID);
    }
}
