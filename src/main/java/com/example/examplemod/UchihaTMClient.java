package com.example.examplemod;

import com.example.examplemod.client.ManaHUD;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

@Mod(value = UchihaTM.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class UchihaTMClient {

    public static KeyMapping SHARINGAN_KEY;

    public UchihaTMClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        UchihaTM.LOGGER.info("HELLO FROM CLIENT SETUP");
        UchihaTM.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        event.enqueueWork(() -> {
            SHARINGAN_KEY = new KeyMapping(
                    "key.uchihatm.sharingan",
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_R,
                    "key.categories.uchihatm"
            );

            // Регистрация кейбинда через клиентский Registry
            net.neoforged.neoforge.client.settings.KeyMappingRegistry.register(SHARINGAN_KEY);
        });
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || SHARINGAN_KEY == null) return;

        while (SHARINGAN_KEY.consumeClick()) {
            ManaHUD.toggleSharingan();

            // Опционально: сообщение в чат при переключении
            boolean active = ManaHUD.isSharinganActive();
            String msgKey = active ? "message.uchihatm.sharingan_on" : "message.uchihatm.sharingan_off";
            String msg = I18n.get(msgKey);
            player.displayClientMessage(net.minecraft.network.chat.Component.literal(msg), true);
        }
    }
}
