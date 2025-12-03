// внутри setMax(...)
private static int setMax(CommandSourceStack source, ServerPlayer target, int value) {
    var uuid = target.getUUID();

    if (!ManaManager.isInitialized(uuid)) {
        source.sendFailure(Component.literal("§cИгрок не инициализирован. Используй: /uchihatm init <ник>"));
        return 0;
    }

    int clamped = Math.max(0, value);
    ManaManager.getMana(uuid).setMaxMana(clamped);
    if (ManaManager.getCurrentMana(uuid) > clamped) {
        ManaManager.getMana(uuid).setCurrentMana(clamped);
    }

    ManaPersistence.saveMana(uuid, ManaManager.getMana(uuid), target);

    final int shown = clamped;
    source.sendSuccess(
            () -> Component.literal("§6Максимальная мана для §f" + target.getName().getString()
                    + " §6установлена на §f" + shown),
            false
    );
    return 1;
}
