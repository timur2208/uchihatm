package com.example.examplemod.attachment;

public class ManaAttachment {
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
}
