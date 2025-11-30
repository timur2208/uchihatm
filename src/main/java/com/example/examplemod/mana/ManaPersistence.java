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
 * Сохранение и загрузка маны в JSON файл (отдельно для каждого мира/сервера)
 */
public class ManaPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Получить корневую папку маны (отдельная для каждого сервера)
     */
    private static File getManaRootDir() {
        // Используем имя сервера или worldname как идентификатор
        // Это гарантирует что разные серверы имеют разные папки
        File worldDir = new File(".");
        String serverName = System.getProperty("server.name", "default");

        File manaDir = new File(worldDir, "mana_data_" + serverName);
        if (!manaDir.exists()) {
            manaDir.mkdirs();
        }
        return manaDir;
    }

    /**
     * Получить путь к файлу маны игрока (с учётом мира)
     */
    private static File getManaFile(UUID playerUUID, ServerPlayer player) {
        String dimensionName = player.serverLevel().dimension().location().toString()
                .replace(":", "_");

        File dimensionDir = new File(getManaRootDir(), dimensionName);
        if (!dimensionDir.exists()) {
            dimensionDir.mkdirs();
        }

        return new File(dimensionDir, playerUUID + ".json");
    }

    /**
     * Сохранить ману в файл
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
     * Загрузить ману из файла
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
