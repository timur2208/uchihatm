package com.example.examplemod.command;

import com.example.examplemod.attachment.ManaAttachments;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ManaCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("uchihatm")
                        .then(Commands.literal("mana")
                                .then(Commands.literal("get")
                                        .executes(context -> {
                                            ServerPlayer player = context.getSource().getPlayerOrException();
                                            var mana = player.getData(ManaAttachments.MANA);
                                            context.getSource().sendSuccess(
                                                    () -> Component.literal("§6Мана: §f" + mana.getCurrentMana() + "/" + mana.getMaxMana()),
                                                    false
                                            );
                                            return 1;
                                        })
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0, 1000))
                                                .executes(context -> {
                                                    int value = IntegerArgumentType.getInteger(context, "value");
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    var mana = player.getData(ManaAttachments.MANA);
                                                    mana.setCurrentMana(value);
                                                    context.getSource().sendSuccess(
                                                            () -> Component.literal("§6Мана установлена: §f" + mana.getCurrentMana() + "/" + mana.getMaxMana()),
                                                            false
                                                    );
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("add")
                                        .then(Commands.argument("value", IntegerArgumentType.integer(-1000, 1000))
                                                .executes(context -> {
                                                    int value = IntegerArgumentType.getInteger(context, "value");
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    var mana = player.getData(ManaAttachments.MANA);
                                                    mana.addMana(value);
                                                    context.getSource().sendSuccess(
                                                            () -> Component.literal("§6Мана изменена: §f" + mana.getCurrentMana() + "/" + mana.getMaxMana()),
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
