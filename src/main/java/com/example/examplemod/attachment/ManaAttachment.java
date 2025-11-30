package com.example.examplemod.attachment;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.INBTSerializable;

public class ManaAttachment implements INBTSerializable<CompoundTag> {
    private int currentMana = 50;
    private int maxMana = 100;

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int mana) {
        this.currentMana = Math.max(0, Math.min(mana, maxMana));
    }

    public void addMana(int amount) {
        setCurrentMana(currentMana + amount);
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void reset() {
        currentMana = 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentMana", currentMana);
        tag.putInt("maxMana", maxMana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        currentMana = nbt.getInt("currentMana");
        maxMana = nbt.getInt("maxMana");
    }
}
