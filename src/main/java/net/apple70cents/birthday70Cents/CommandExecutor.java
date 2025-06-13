package net.apple70cents.birthday70Cents;

import net.apple70cents.birthday70Cents.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandExecutor implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        String cmd = args.length == 0 ? "help" : args[0].toLowerCase();
        switch (cmd) {
            case "set":
                return new setCommand().work(commandSender, command, s, args);
            case "withdraw":
                return new withdrawCommand().work(commandSender, command, s, args);
            case "edit":
                return new editCommand().work(commandSender, command, s, args);
            case "get":
                return new getCommand().work(commandSender, command, s, args);
            case "query":
                return new queryCommand().work(commandSender, command, s, args);
            case "modify":
                return new modifyCommand().work(commandSender, command, s, args);
            case "reload":
                return new reloadCommand().work(commandSender, command, s, args);
            case "help":
                return new helpCommand().work(commandSender, command, s, args);
            default:
                return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        String cmd = args.length == 0 ? "help" : args[0].toLowerCase();
        switch (cmd) {
            case "set":
                return new setCommand().getTabComplete(commandSender, command, s, args);
            case "withdraw":
                return new withdrawCommand().getTabComplete(commandSender, command, s, args);
            case "edit":
                return new editCommand().getTabComplete(commandSender, command, s, args);
            case "get":
                return new getCommand().getTabComplete(commandSender, command, s, args);
            case "query":
                return new queryCommand().getTabComplete(commandSender, command, s, args);
            case "modify":
                return new modifyCommand().getTabComplete(commandSender, command, s, args);
            case "reload":
                return new reloadCommand().getTabComplete(commandSender, command, s, args);
            case "help":
            default:
                if (args.length <= 1) {
                    return new helpCommand().getTabComplete(commandSender, command, s, args);
                }
                return List.of();
        }
    }
}
