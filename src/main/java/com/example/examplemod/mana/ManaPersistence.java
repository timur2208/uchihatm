package com.example.examplemod.mana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;

public class ManaPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Уникальный ID сервера по абсолютному пути level.dat
     */
    private static String getServerId(ServerPlayer player) {
        try {
            File levelDat = player.getServer()
                    .getWorldPath(LevelResource.ROOT)
                    .resolve("level.dat")
                    .toFile();

            String path = levelDat.getAbsolutePath();
            // Хеш пути как стабильный ID
            return Integer.toHexString(path.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
            // Фоллбек — но это уже крайний случай
            return player.getServer().getWorldData().getLevelName();
        }
    }

    /**
     * Папка маны для конкретного сервера
     * Пример: mana_data/3fa4c9ab/
     */
    private static File getManaServerDir(ServerPlayer player) {
        String serverId = getServerId(player);
        File dir = new File("mana_data", serverId);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getManaFile(UUID playerUUID, ServerPlayer player) {
        File serverDir = getManaServerDir(player);
        return new File(serverDir, playerUUID + ".json");
    }

    public static void saveMana(UUID playerUUID, ManaData mana, ServerPlayer player) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("uuid", playerUUID.toString());
            json.addProperty("currentMana", mana.getCurrentMana());
            json.addProperty("maxMana", mana.getMaxMana());
            json.addProperty("initialized", ManaManager.isInitialized(playerUUID));

            File file = getManaFile(playerUUID, player);
            Files.write(file.toPath(), GSON.toJson(json).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMana(UUID playerUUID, ServerPlayer player) {
        try {
            File file = getManaFile(playerUUID, player);
            if (!file.exists()) {
                return;
            }

            String content = new String(Files.readAllBytes(file.toPath()));
            JsonObject json = GSON.fromJson(content, JsonObject.class);

            boolean initialized = json.has("initialized") && json.get("initialized").getAsBoolean();
            if (!initialized) {
                return;
            }

            int currentMana = json.get("currentMana").getAsInt();
            int maxMana = json.get("maxMana").getAsInt();

            ManaManager.initializePlayer(playerUUID);
            ManaData mana = ManaManager.getMana(playerUUID);
            mana.setMaxMana(maxMana);
            mana.setCurrentMana(currentMana);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
