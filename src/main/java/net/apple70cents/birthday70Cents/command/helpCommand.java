package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class helpCommand {
    private final List<Pair<String, String>> CMD_LIST = List.of(
            Pair.of("basic", "help"),
            Pair.of("basic", "set"),
            Pair.of("basic", "withdraw"),
            Pair.of("admin", "edit"),
            Pair.of("admin", "get"),
            Pair.of("admin", "query"),
            Pair.of("admin", "modify"),
            Pair.of("admin", "reload")
    );

    private List<String> permsFiltered = new ArrayList<>();

    public void refreshPermsFilteredList(@NotNull CommandSender commandSender) {
        permsFiltered = new ArrayList<>();
        for (Pair<String, String> cmd : CMD_LIST) {
            if (commandSender.hasPermission("birthday." + cmd.getLeft() + "." + cmd.getRight())) {
                permsFiltered.add(cmd.getRight());
            }
        }
    }

    public boolean work(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!commandSender.hasPermission("birthday.basic.help")) return false;

        refreshPermsFilteredList(commandSender);
        for (String cmd : permsFiltered) {
            commandSender.sendRichMessage(I18n.trans("help-menu." + cmd));
        }
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        refreshPermsFilteredList(commandSender);
        return permsFiltered.isEmpty() ? List.of() : permsFiltered;
    }
}
