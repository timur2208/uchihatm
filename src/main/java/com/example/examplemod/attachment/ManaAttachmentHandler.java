package com.example.examplemod.attachment;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.minecraft.world.entity.player.Player;

public class ManaAttachmentHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getData(ManaAttachments.MANA).setCurrentMana(50);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        event.getEntity().getData(ManaAttachments.MANA).reset();
    }
}
