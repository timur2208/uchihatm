package com.example.examplemod.mana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;

/**
 * Сохранение и загрузка маны в JSON файл
 * Каждый мир (сервер) имеет одну общую ман для всех игроков
 */
public class ManaPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Получить папку мира с маной игроков
     */
    private static File getManaWorldDir(ServerPlayer player) {
        // Получаем имя мира (папки сохранений)
        String worldName = player.getServer().getWorldData().getLevelName();

        // Создаём папку: mana_data/<worldname>/
        File manaDir = new File("mana_data", worldName);
        if (!manaDir.exists()) {
            manaDir.mkdirs();
        }
        return manaDir;
    }

    /**
     * Получить файл маны игрока (без разделения по измерениям)
     */
    private static File getManaFile(UUID playerUUID, ServerPlayer player) {
        File worldDir = getManaWorldDir(player);
        return new File(worldDir, playerUUID + ".json");
    }

    /**
     * Сохранить ману в файл мира
     */
    public static void saveMana(UUID playerUUID, ManaData mana, ServerPlayer player) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("uuid", playerUUID.toString());
            json.addProperty("currentMana", mana.getCurrentMana());
            json.addProperty("maxMana", mana.getMaxMana());

            File file = getManaFile(playerUUID
