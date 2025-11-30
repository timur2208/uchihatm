package com.example.examplemod.mana;

/**
 * Данные маны одного игрока
 */
public class ManaData {
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

    public void setMaxMana(int max) {
        this.maxMana = Math.max(1, max);
        // Если текущая ман больше максимальной, уменьшаем
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    public void reset() {
        currentMana = 0;
    }
}
