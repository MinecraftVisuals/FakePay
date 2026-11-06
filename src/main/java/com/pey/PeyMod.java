package com.pey;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

public class PeyMod implements ModInitializer {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,##0.##");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                CommandManager.literal("pay")
                    .then(CommandManager.argument("username", StringArgumentType.word())
                        .then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0.01))
                            .executes(context -> {
                                String username = StringArgumentType.getString(context, "username");
                                double amount = DoubleArgumentType.getDouble(context, "amount");

                                Text message = Text.literal("You paid " + username + " ")
                                    .formatted(Formatting.WHITE)
                                    .append(Text.literal("$").formatted(Formatting.GREEN))
                                    .append(Text.literal(FORMAT.format(amount)).formatted(Formatting.WHITE));

                                context.getSource().sendFeedback(() -> message, false);
                                return 1;
                            })
                        )
                    )
            );
        });
    }
}
