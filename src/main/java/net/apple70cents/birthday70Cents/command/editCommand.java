package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.gui.BirthdayEditGui;
import net.apple70cents.birthday70Cents.util.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class editCommand {
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!sender.hasPermission("birthday.admin.edit")) return false;
        if (!(sender instanceof Player)) {
            sender.sendRichMessage(I18n.trans("feedback.error-non-player"));
            return true;
        }

        ((Player) sender).openInventory(new BirthdayEditGui().getInventory());
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
