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
 * Каждый сервер имеет уникальную папку на основе level.dat
 */
public class ManaPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Получить уникальный хеш сервера
     */
    private static String getServerHash(ServerPlayer player) {
        try {
            File levelDat = player.getServer().getWorldPath(net.minecraft.world.level.storage.LevelResource.ROOT)
                    .resolve("level.dat").toFile();

            if (levelDat.exists()) {
                // Используем абсолютный путь как уникальный идентификатор
                String absolutePath = levelDat.getAbsolutePath();
                // Берём хеш пути
                return String.format("%08x", absolutePath.hashCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fallback: используем имя мира
        return player.getServer().getWorldData().getLevelName();
    }

    /**
     * Получить папку сервера с маной игроков
     */
    private static File getManaServerDir(ServerPlayer player) {
        String serverHash = getServerHash(player);

        // Создаём папку: mana_data/<server_hash>/
        File manaDir = new File("mana_data", serverHash);
        if (!manaDir.exists()) {
            manaDir.mkdirs();
        }
        return manaDir;
    }

    /**
     * Получить файл маны игрока
     */
    private static File getManaFile(UUID playerUUID, ServerPlayer player) {
        File serverDir = getManaServerDir(player);
        return new File(serverDir, playerUUID + ".json");
    }

    /**
     * Сохранить ману в файл сервера
     */
    public static void saveMana(UUID playerUUID, ManaData mana, ServerPlayer player) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("uuid", playerUUID.toString());
            json.addProperty("currentMana", mana.getCurrentMana());
            json.addProperty("maxMana", mana.getMaxMana());

            File file = getManaFile(playerUUID, player);
            Files.write(file.toPath(), GSON.toJson(json).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузить ману из файла сервера
     */
    public static void loadMana(UUID playerUUID, ServerPlayer player) {
        try {
            File file = getManaFile(playerUUID, player);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                JsonObject json = GSON.fromJson(content, JsonObject.class);

                int currentMana = json.get("currentMana").getAsInt();
                int maxMana = json.get("maxMana").getAsInt();

                ManaData mana = ManaManager.getMana(playerUUID);
                mana.setCurrentMana(currentMana);
                mana.setMaxMana(maxMana);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
