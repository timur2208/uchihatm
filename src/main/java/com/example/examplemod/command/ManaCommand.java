package com.example.examplemod.command;

import com.example.examplemod.mana.ManaManager;
import com.example.examplemod.mana.ManaPersistence;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Команды для управления маной
 */
public class ManaCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("uchihatm")
                        .then(Commands.literal("mana")
                                .then(Commands.literal("get")
                                        .executes(context -> {
                                            ServerPlayer player = context.getSource().getPlayerOrException();
                                            int current = ManaManager.getCurrentMana(player.getUUID());
                                            int max = ManaManager.getMaxMana(player.getUUID());
                                            context.getSource().sendSuccess(
                                                    () -> Component.literal("§6Мана: §f" + current + "/" + max),
                                                    false
                                            );
                                            return 1;
                                        })
                                )
                                .then(Commands.literal("set")
                                        .requires(source -> source.hasPermission(2))
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0, 1000))
                                                .executes(context -> {
                                                    int value = IntegerArgumentType.getInteger(context, "value");
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    ManaManager.getMana(player.getUUID()).setCurrentMana(value);
                                                    ManaPersistence.saveMana(player.getUUID(), ManaManager.getMana(player.getUUID()), player);
                                                    int current = ManaManager.getCurrentMana(player.getUUID());
                                                    int max = ManaManager.getMaxMana(player.getUUID());
                                                    context.getSource().sendSuccess(
                                                            () -> Component.literal("§6Мана установлена: §f" + current + "/" + max),
                                                            false
                                                    );
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("add")
                                        .requires(source -> source.hasPermission(2))
                                        .then(Commands.argument("value", IntegerArgumentType.integer(-1000, 1000))
                                                .executes(context -> {
                                                    int value = IntegerArgumentType.getInteger(context, "value");
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    ManaManager.addMana(player.getUUID(), value);
                                                    ManaPersistence.saveMana(player.getUUID(), ManaManager.getMana(player.getUUID()), player);
                                                    int current = ManaManager.getCurrentMana(player.getUUID());
                                                    int max = ManaManager.getMaxMana(player.getUUID());
                                                    context.getSource().sendSuccess(
                                                            () -> Component.literal("§6Мана изменена: §f" + current + "/" + max),
                                                            false
                                                    );
                                                    return 1;
                                                })
                                        )
                                )
                        )
        );
    }
}
