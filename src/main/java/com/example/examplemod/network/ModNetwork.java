package com.example.examplemod.network;

import com.example.examplemod.UchihaTM;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public class ModNetwork {

    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(UchihaTM.MODID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(
                id++,
                SharinganTogglePacket.class,
                SharinganTogglePacket::encode,
                SharinganTogglePacket::decode,
                SharinganTogglePacket::handle
        );
    }
}
