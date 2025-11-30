package com.example.examplemod.mana;

/**
 * Данные маны игрока
 */
public class ManaData {
    private int currentMana;
    private int maxMana;
    private int regenTicks = 0;
    private static final int REGEN_INTERVAL = 10; // регенерируем каждые 10 тиков

    /**
     * Конструктор без параметров (по умолчанию)
     */
    public ManaData() {
        this.currentMana = 0;
        this.maxMana = 0;
    }

    /**
     * Конструктор с параметрами
     */
    public ManaData(int currentMana, int maxMana) {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

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
        this.maxMana = max;
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    /**
     * Добавить ман
     */
    public void addMana(int amount) {
        setCurrentMana(currentMana + amount);
    }

    /**
     * Обновить регенерацию (вызывается каждый тик)
     */
    public void updateRegen() {
        regenTicks++;
        if (regenTicks >= REGEN_INTERVAL) {
            regenTicks = 0;
            if (currentMana < maxMana) {
                currentMana++;
            }
        }
    }
}
