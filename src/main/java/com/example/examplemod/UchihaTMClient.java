package com.example.examplemod;

import com.example.examplemod.client.ManaHUD;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.glfw.GLFW;

@Mod(value = UchihaTM.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class UchihaTMClient {

    // Клавиша для Шарингана (по умолчанию: R)
    public static KeyMapping SHARINGAN_KEY;

    public UchihaTMClient(ModContainer container) {
        // Экран конфига (как было)
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        UchihaTM.LOGGER.info("HELLO FROM CLIENT SETUP");
        UchihaTM.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        // Регистрируем клавишу
        event.enqueueWork(() -> {
            SHARINGAN_KEY = new KeyMapping(
                    "key.uchihatm.sharingan",                  // id
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    GLFW.GLFW_KEY_R,                           // по умолчанию R
                    "key.categories.uchihatm"                  // категория
            );
            net.neoforged.neoforge.client.ClientRegistry.registerKeyBinding(SHARINGAN_KEY);
        });
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player == null) return;
        if (SHARINGAN_KEY == null) return;

        // При нажатии – переключаем флаг
        while (SHARINGAN_KEY.consumeClick()) {
            ManaHUD.toggleSharingan();
        }
    }
}
