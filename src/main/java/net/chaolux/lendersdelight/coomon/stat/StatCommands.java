package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import jdk.jshell.SourceCodeAnalysis;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.stringtemplate.v4.ST;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE)
public class StatCommands {
    private static final DynamicCommandExceptionType DYNAMIC_COMMAND_EXCEPTION_TYPE=new DynamicCommandExceptionType(stat -> Component.translatable("command.lendersdelight.stat.invalid",stat));

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent registerCommandsEvent) {
        CommandDispatcher<CommandSourceStack> commandSourceStackCommandDispatcher=registerCommandsEvent.getDispatcher();
        commandSourceStackCommandDispatcher.register(Commands.literal("lendersdelight").requires(commandSourceStack -> commandSourceStack.hasPermission(2)).then(Commands.literal("stat").then(Commands.literal("set").then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("stat", StringArgumentType.word()).suggests(StatCommands::suggestStat).then(Commands.argument("value", FloatArgumentType.floatArg(0.0f,1000000.0f)).executes(commandContext -> set(commandContext,Command.SET)))))).then(Commands.literal("add").then(Commands.argument("player",EntityArgument.players()).then(Commands.argument("stat",StringArgumentType.word()).suggests(StatCommands::suggestStat).then(Commands.argument("value",FloatArgumentType.floatArg(0.0f,1000000.0f)).executes(commandContext -> set(commandContext,Command.ADD)))))).then(Commands.literal("remove").then(Commands.argument("player",EntityArgument.players()).then(Commands.argument("stat",StringArgumentType.word()).suggests(StatCommands::suggestStat).then(Commands.argument("value",FloatArgumentType.floatArg(0.0f,1000000.0f)).executes(commandContext -> set(commandContext,Command.REMOVE)))))).then(Commands.literal("reset").then(Commands.argument("player",EntityArgument.players()).executes(StatCommands::reset).then(Commands.argument("stat",StringArgumentType.word()).suggests(StatCommands::suggestStat).executes(StatCommands::resetOnce)))).then(Commands.literal("get").then(Commands.argument("player",EntityArgument.players()).then(Commands.argument("stat",StringArgumentType.word()).suggests(StatCommands::suggestStat).executes(StatCommands::get)))).then(Commands.literal("foodreset").then(Commands.argument("player",EntityArgument.players()).executes(StatCommands::resetFood)))));
    }

    private static CompletableFuture<Suggestions> suggestStat(CommandContext<CommandSourceStack> commandSourceStackCommandContext, SuggestionsBuilder suggestionsBuilder) {
        String string=suggestionsBuilder.getRemaining().toUpperCase(Locale.ROOT);
        for(StatType statType : StatType.values()) {
            if(statType.name().startsWith(string)) suggestionsBuilder.suggest(statType.name());
        }
        return suggestionsBuilder.buildFuture();
    }

    private static StatType getType(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        String string=StringArgumentType.getString(commandSourceStackCommandContext,"stat");
        try {
            return StatType.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw DYNAMIC_COMMAND_EXCEPTION_TYPE.create(string);
        }
    }

    private static int set(CommandContext<CommandSourceStack> commandSourceStackCommandContext,Command command) throws CommandSyntaxException {
        Collection<ServerPlayer> serverPlayerCollection=EntityArgument.getPlayers(commandSourceStackCommandContext,"player");
        StatType statType=getType(commandSourceStackCommandContext);
        float value=FloatArgumentType.getFloat(commandSourceStackCommandContext,"value");
        int data=0;
        for(ServerPlayer serverPlayer : serverPlayerCollection) {
            if(serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> {
                switch (command) {
                    case SET -> stat.setStat(statType,value);
                    case ADD -> stat.addStat(statType,value);
                    case REMOVE -> stat.setStat(statType,Math.max(0.0f,stat.getStat(statType) - value));
                }
                return true;
            }).orElse(false)) {
                PlayerStatProvider.sync(serverPlayer);
                data++;
            }
        }
        int index=data;
        Component component=switch (command) {
            case SET -> Component.translatable("command.lendersdelight.stat.set",Component.translatable(statType.getLangKey()),value,index);
            case ADD -> Component.translatable("command.lendersdelight.stat.add",value,Component.translatable(statType.getLangKey()),index);
            case REMOVE -> Component.translatable("command.lendersdelight.stat.remove",value,Component.translatable(statType.getLangKey()),index);
        };
        commandSourceStackCommandContext.getSource().sendSuccess(() -> component,true);
        return data;
    }

    private static int resetOnce(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        StatType statType=getType(commandSourceStackCommandContext);
        Collection<ServerPlayer> serverPlayerCollection=EntityArgument.getPlayers(commandSourceStackCommandContext,"player");
        int data=0;
        for(ServerPlayer serverPlayer : serverPlayerCollection) {
            if(serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> {
                stat.setStat(statType,0.0f);
                return true;
            }).orElse(false)) {
                PlayerStatProvider.sync(serverPlayer);
                data++;
            }
        }
        int value=data;
        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.translatable("command.lendersdelight.stat.reset_once",Component.translatable(statType.getLangKey()),value),true);
        return data;
    }

    private static int reset(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        Collection<ServerPlayer> serverPlayerCollection=EntityArgument.getPlayers(commandSourceStackCommandContext,"player");
        int data=0;
        for(ServerPlayer serverPlayer : serverPlayerCollection) {
            if(serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> {
                for (StatType statType : StatType.values()) {
                    stat.setStat(statType,0.0f);
                }
                stat.setFoodUses(Map.of());
                return true;
            }).orElse(false)) {
                PlayerStatProvider.sync(serverPlayer);
                data++;
            }
        }
        int value=data;
        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.translatable("command.lendersdelight.stat.reset",value),true);
        return data;
    }

    private static int resetFood(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        Collection<ServerPlayer> serverPlayerCollection=EntityArgument.getPlayers(commandSourceStackCommandContext,"player");
        int data=0;
        for(ServerPlayer serverPlayer : serverPlayerCollection) {
            if(serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> {
                stat.setFoodUses(Map.of());
                return true;
            }).orElse(false)) {
                PlayerStatProvider.sync(serverPlayer);
                data++;
            }
        }
        int value=data;
        commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.translatable("command.lendersdelight.stat.reset_food",value),true);
        return data;
    }

    private static int get(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        StatType statType=getType(commandSourceStackCommandContext);
        Collection<ServerPlayer> serverPlayerCollection=EntityArgument.getPlayers(commandSourceStackCommandContext,"player");
        for (ServerPlayer serverPlayer : serverPlayerCollection) {
            float value=serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> stat.getStat(statType)).orElse(0.0f);
            commandSourceStackCommandContext.getSource().sendSuccess(() -> Component.translatable("command.lendersdelight.stat.get",serverPlayer.getDisplayName(),Component.translatable(statType.getLangKey()),value),true);
        }
        return serverPlayerCollection.size();
    }
}
