package com.example.examplemod.mana;

/**
 * Данные маны одного игрока
 */
public class ManaData {
    private int currentMana = 50;
    private int maxMana = 100;
    private long lastRegenTime = System.currentTimeMillis();
    private static final int REGEN_AMOUNT = 1; // Маны за раз
    private static final long REGEN_INTERVAL = 1000; // 1 сек между регенерацией (мс)

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
        if (currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    public void reset() {
        currentMana = 0;
    }

    /**
     * Обновить регенерацию маны
     * Вызывается каждый тик сервера (20 раз в сек)
     */
    public void updateRegen() {
        long currentTime = System.currentTimeMillis();

        // Проверяем, прошла ли достаточно времени для регенерации
        if (currentTime - lastRegenTime >= REGEN_INTERVAL) {
            // Если ман не полная, восстанавливаем
            if (currentMana < maxMana) {
                addMana(REGEN_AMOUNT);
            }
            lastRegenTime = currentTime;
        }
    }
}
