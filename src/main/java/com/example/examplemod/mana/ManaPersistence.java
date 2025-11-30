package com.example.examplemod.mana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Сохранение и загрузка маны в JSON файл
 */
public class ManaPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File MANA_DIR = new File("mana_data");

    static {
        if (!MANA_DIR.exists()) {
            MANA_DIR.mkdirs();
        }
    }

    /**
     * Получить путь к файлу маны игрока
     */
    private static File getManaFile(UUID playerUUID) {
        return new File(MANA_DIR, playerUUID + ".json");
    }

    /**
     * Сохранить ману в файл
     */
    public static void saveMana(UUID playerUUID, ManaData mana) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("uuid", playerUUID.toString());
            json.addProperty("currentMana", mana.getCurrentMana());
            json.addProperty("maxMana", mana.getMaxMana());

            File file = getManaFile(playerUUID);
            Files.write(file.toPath(), GSON.toJson(json).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузить ману из файла
     */
    public static void loadMana(UUID playerUUID) {
        try {
            File file = getManaFile(playerUUID);
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
