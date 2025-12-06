public void handle(IPayloadContext context) {
    context.enqueueWork(() -> {
        ServerPlayer player = (ServerPlayer) context.player();
        if (player == null) return;

        var uuid = player.getUUID();
        boolean now = !ManaManager.isSharinganActive(uuid);
        ManaManager.setSharingan(uuid, now);

        // Синхронизация флага Шарингана на клиент
        net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                player,
                new SharinganSyncPacket(now)
        );

        if (now) {
            // Включаем только скорость
            MobEffectInstance speed = new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    20 * 60 * 60,
                    0,
                    false,
                    false,
                    true
            );
            player.addEffect(speed);
        } else {
            // Выключаем скорость
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    });
}
