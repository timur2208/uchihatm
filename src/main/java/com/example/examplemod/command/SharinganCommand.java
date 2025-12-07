package com.example.examplemod.command;

import com.example.examplemod.mana.SharinganData;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class SharinganCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sharingan_toggle")
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    ServerPlayer player = source.getPlayer();
                    if (player == null) return 0;

                    SharinganData.toggle(player);
                    boolean active = SharinganData.isSharinganActive(player);

                    if (active) {
                        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED,
                                20 * 60 * 60,
                                0,
                                false,
                                false,
                                true
                        ));
                    } else {
                        player.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED);
                    }

                    return 1;
                })
        );
    }
}
