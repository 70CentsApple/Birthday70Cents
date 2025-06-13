package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.util.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class reloadCommand {
    public boolean work(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!commandSender.hasPermission("birthday.admin.reload")) return false;
        Birthday70Cents.init();
        commandSender.sendRichMessage(I18n.trans("feedback.reload"));
        return true;
    }

    public List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
