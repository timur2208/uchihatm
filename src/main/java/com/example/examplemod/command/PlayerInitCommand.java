package com.example.examplemod.command;

import java.util.UUID;

import com.example.examplemod.mana.ManaManager;
import com.example.examplemod.mana.ManaPersistence;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Команда для инициализации игрока в системе мода
 */
public class PlayerInitCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("uchihatm")
                        .then(Commands.literal("init")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> {
                                            ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
                                            UUID playerUUID = targetPlayer.getUUID();

                                            // Инициализируем ману (50/100)
                                            ManaManager.getMana(playerUUID).setCurrentMana(50);

                                            // Сохраняем в файл ЭТОГО МИРА
                                            ManaPersistence.saveMana(playerUUID, ManaManager.getMana(playerUUID), targetPlayer);

                                            // Сообщение администратору
                                            context.getSource().sendSuccess(
                                                    () -> Component.literal("§6✓ Игрок §f" + targetPlayer.getName().getString() +
                                                            "§6 инициализирован! Ман: §f50/100"),
                                                    false
                                            );

                                            // Сообщение игроку
                                            targetPlayer.displayClientMessage(
                                                    Component.literal("§6Вы инициализированы в системе мода! Ман: §f50/100"),
                                                    false
                                            );

                                            return 1;
                                        })
                                )
                        )
        );
    }
}
