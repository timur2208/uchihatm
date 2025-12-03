package com.example.examplemod.command;

import com.example.examplemod.mana.ManaManager;
import com.example.examplemod.mana.ManaPersistence;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class PlayerMaxManaCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("uchihatm")
                        .then(Commands.literal("mana")
                                .then(Commands.literal("max")
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", IntegerArgumentType.integer(0, 100000))
                                                        .executes(ctx -> {
                                                            ServerPlayer self = ctx.getSource().getPlayerOrException();
                                                            return setMax(ctx.getSource(), self, IntegerArgumentType.getInteger(ctx, "value"));
                                                        })
                                                        .then(Commands.argument("player", EntityArgument.player())
                                                                .requires(src -> src.hasPermission(2))
                                                                .executes(ctx -> {
                                                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                                                                    int value = IntegerArgumentType.getInteger(ctx, "value");
                                                                    return setMax(ctx.getSource(), target, value);
                                                                })
                                                        )
                                                )
                                        )
                                        .then(Commands.literal("add")
                                                .then(Commands.argument("delta", IntegerArgumentType.integer(-100000, 100000))
                                                        .executes(ctx -> {
                                                            ServerPlayer self = ctx.getSource().getPlayerOrException();
                                                            return addMax(ctx.getSource(), self, IntegerArgumentType.getInteger(ctx, "delta"));
                                                        })
                                                        .then(Commands.argument("player", EntityArgument.player())
                                                                .requires(src -> src.hasPermission(2))
                                                                .executes(ctx -> {
                                                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                                                                    int delta = IntegerArgumentType.getInteger(ctx, "delta");
                                                                    return addMax(ctx.getSource(), target, delta);
                                                                })
                                                        )
                                                )
                                        )
                                )
                        )
        );
    }

    private static int setMax(CommandSourceStack source, ServerPlayer target, int value) {
        var uuid = target.getUUID();

        if (!ManaManager.isInitialized(uuid)) {
            source.sendFailure(Component.literal("§cИгрок не инициализирован. Используй: /uchihatm init <ник>"));
            return 0;
        }

        value = Math.max(0, value);
        ManaManager.getMana(uuid).setMaxMana(value);
        // Притянуть current вниз, если превысил
        if (ManaManager.getCurrentMana(uuid) > value) {
            ManaManager.getMana(uuid).setCurrentMana(value);
        }

        // Сохранить в рамках текущего сервера
        ManaPersistence.saveMana(uuid, ManaManager.getMana(uuid), target);

        source.sendSuccess(() -> Component.literal("§6Максимальная мана для §f" + target.getName().getString() + " §6установлена на §f" + value), false);
        return 1;
    }

    private static int addMax(CommandSourceStack source, ServerPlayer target, int delta) {
        var uuid = target.getUUID();

        if (!ManaManager.isInitialized(uuid)) {
            source.sendFailure(Component.literal("§cИгрок не инициализирован. Используй: /uchihatm init <ник>"));
            return 0;
        }

        int newMax = Math.max(0, ManaManager.getMaxMana(uuid) + delta);
        ManaManager.getMana(uuid).setMaxMana(newMax);
        if (ManaManager.getCurrentMana(uuid) > newMax) {
            ManaManager.getMana(uuid).setCurrentMana(newMax);
        }

        ManaPersistence.saveMana(uuid, ManaManager.getMana(uuid), target);

        String sign = delta >= 0 ? "+" : "";
        source.sendSuccess(() -> Component.literal("§6Макс. мана для §f" + target.getName().getString() + " §6изменена: §f" + sign + delta + " §7(теперь " + newMax + ")"), false);
        return 1;
    }
}
