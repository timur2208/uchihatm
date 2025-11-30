package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;

/**
 * Класс для хранения данных маны игрока
 */
public class ManaData {
    public static final int MAX_MANA = 100;

    private int currentMana = 0;
    private int maxMana = MAX_MANA;

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int mana) {
        this.currentMana = Math.max(0, Math.min(mana, maxMana));
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int max) {
        this.maxMana = Math.max(1, max);
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    public void addMana(int amount) {
        setCurrentMana(currentMana + amount);
    }

    public void removeMana(int amount) {
        setCurrentMana(currentMana - amount);
    }

    public void reset() {
        currentMana = 0;
    }

    // Сохранение/загрузка из NBT
    public void saveToNBT(CompoundTag tag) {
        tag.putInt("CurrentMana", currentMana);
        tag.putInt("MaxMana", maxMana);
    }

    public void loadFromNBT(CompoundTag tag) {
        currentMana = tag.getInt("CurrentMana");
        maxMana = tag.getInt("MaxMana");
    }
}
