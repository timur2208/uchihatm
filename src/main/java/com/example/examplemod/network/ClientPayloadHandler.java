package com.example.examplemod.network;

import java.util.function.Supplier;

import com.example.examplemod.client.ManaHUDClientData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleManaData(ManaDataPayload payload, Supplier<IPayloadContext> context) {
        context.get().enqueueWork(() -> {
            ManaHUDClientData.setMana(payload.getCurrentMana(), payload.getMaxMana());
        });
    }
}
