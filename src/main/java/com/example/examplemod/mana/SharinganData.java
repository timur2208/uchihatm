package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SharinganData {

    public static boolean isSharinganActive(Player player) {
        CompoundTag tag = player.getPersistentData();
        return tag.getBoolean("SharinganActive");
    }

    public static void setSharinganActive(Player player, boolean active) {
        CompoundTag tag = player.getPersistentData();
        tag.putBoolean("SharinganActive", active);
    }

    public static void toggle(Player player) {
        boolean now = !isSharinganActive(player);
        setSharinganActive(player, now);
    }
}
