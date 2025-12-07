package com.example.examplemod.client;

import com.example.examplemod.UchihaTM;
import com.example.examplemod.mana.SharinganData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = UchihaTM.MODID, value = Dist.CLIENT)
public class SharinganKeyEvent {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || ClientKeyMappings.SHARINGAN_KEY == null) return;

        if (ClientKeyMappings.SHARINGAN_KEY.consumeClick()) {
            // Отправляем команду на сервер для изменения Sharingan
            mc.getConnection().sendCommand("sharingan_toggle");
            // Меняем цвет на клиенте
            ManaHUD.toggleSharingan();
        }
    }
}
