package com.example.examplemod.network;

public class ManaHUDClientData {
    private static int clientMana = 0;
    private static int clientMaxMana = 100;

    public static void setMana(int current, int max) {
        clientMana = current;
        clientMaxMana = max;
    }

    public static int getCurrentMana() {
        return clientMana;
    }

    public static int getMaxMana() {
        return clientMaxMana;
    }
}
