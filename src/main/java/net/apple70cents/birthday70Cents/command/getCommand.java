package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class getCommand {
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!sender.hasPermission("birthday.admin.get")) return false;
        if (!(sender instanceof Player)) {
            sender.sendRichMessage(I18n.trans("feedback.error-non-player"));
            return true;
        }

        ItemStack present = Birthday70Cents.getInstance().getPresent();
        ItemUtils.giveOrDrop((Player) sender, present);
        sender.sendRichMessage(I18n.trans("feedback.withdraw-success"));
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
