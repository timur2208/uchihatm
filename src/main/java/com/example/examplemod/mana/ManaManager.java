package com.example.examplemod.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Глобальный контейнер маны всех игроков
 */
public class ManaManager {
    private static final Map<UUID, ManaData> playerManaMap = new HashMap<>();

    /**
     * Получить ману игрока (создаст если не существует)
     */
    public static ManaData getMana(UUID playerUUID) {
        return playerManaMap.computeIfAbsent(playerUUID, uuid -> new ManaData());
    }

    /**
     * Установить ману игрока
     */
    public static void setMana(UUID playerUUID, int current, int max) {
        ManaData mana = getMana(playerUUID);
        mana.setCurrentMana(current);
        mana.setMaxMana(max);
    }

    /**
     * Добавить ману игроку
     */
    public static void addMana(UUID playerUUID, int amount) {
        getMana(playerUUID).addMana(amount);
    }

    /**
     * Получить текущую ману
     */
    public static int getCurrentMana(UUID playerUUID) {
        return getMana(playerUUID).getCurrentMana();
    }

    /**
     * Получить максимальную ману
     */
    public static int getMaxMana(UUID playerUUID) {
        return getMana(playerUUID).getMaxMana();
    }

    /**
     * Сбросить ману (смерть)
     */
    public static void resetMana(UUID playerUUID) {
        getMana(playerUUID).reset();
    }

    /**
     * Удалить игрока из карты (выход)
     */
    public static void removePlayer(UUID playerUUID) {
        playerManaMap.remove(playerUUID);
    }
}
