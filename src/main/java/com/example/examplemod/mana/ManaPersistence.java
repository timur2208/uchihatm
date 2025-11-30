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
        String worldName = player.getServer().getWorldData().getLevelName();
        File manaDir = new File("mana_data", worldName);
        if (!manaDir.exists()) {
            manaDir.mkdirs();
        }
        return manaDir;
    }

    /**
     * Получить файл маны игрока
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

            File file = getManaFile(playerUUID, player);
            Files.write(file.toPath(), GSON.toJson(json).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузить ману из файла мира
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
