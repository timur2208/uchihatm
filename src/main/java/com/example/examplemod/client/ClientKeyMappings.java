package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientKeyMappings {

    public static KeyMapping SHARINGAN_KEY;

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        SHARINGAN_KEY = new KeyMapping(
                "key.uchihatm.sharingan",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.categories.uchihatm"
        );
        event.register(SHARINGAN_KEY);
    }
}
