package com.example.examplemod.network;

import com.example.examplemod.client.ManaHUD;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * Пакет для синхронизации маны с клиентом
 */
public class ManaClientSyncPacket {
    private final int currentMana;
    private final int maxMana;

    public ManaClientSyncPacket(int currentMana, int maxMana) {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

    public ManaClientSyncPacket(FriendlyByteBuf buf) {
        this.currentMana = buf.readInt();
        this.maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(currentMana);
        buf.writeInt(maxMana);
    }

    public void handle(PlayPayloadContext context) {
        context.enqueueWork(() -> {
            // Обновляем HUD на клиенте
            ManaHUD.clientMana = currentMana;
            ManaHUD.clientMaxMana = maxMana;
        });
    }
}
