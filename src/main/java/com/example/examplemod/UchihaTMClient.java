package com.example.examplemod;

import com.example.examplemod.client.ClientKeyMappings;
import com.example.examplemod.client.ManaHUD;
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
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(value = UchihaTM.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class UchihaTMClient {

    public UchihaTMClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        UchihaTM.LOGGER.info("HELLO FROM CLIENT SETUP");
        UchihaTM.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || ClientKeyMappings.SHARINGAN_KEY == null) return;

        while (ClientKeyMappings.SHARINGAN_KEY.consumeClick()) {
            PacketDistributor.sendToServer(
                    new com.example.examplemod.network.SharinganTogglePacket()
            );
            ManaHUD.toggleSharingan();
        }
    }
}
