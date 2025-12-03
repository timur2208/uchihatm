package com.example.examplemod;

import com.example.examplemod.client.ManaHUD;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
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

        event.registerKeyMapping(
                SHARINGAN_KEY = new KeyMapping(
                        "key.uchihatm.sharingan",              // переводной ключ
                        KeyConflictContext.IN_GAME,
                        KeyModifier.NONE,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_R,                       // по умолчанию R
                        "key.categories.uchihatm"              // категория
                )
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || SHARINGAN_KEY == null) return;

        while (SHARINGAN_KEY.consumeClick()) {
            ManaHUD.toggleSharingan();
        }
    }
}
