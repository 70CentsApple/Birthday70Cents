package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.util.BirthdayStorage;
import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.MonthDay;
import java.util.List;

public class withdrawCommand {
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!sender.hasPermission("birthday.basic.withdraw")) return false;
        if (!(sender instanceof Player)) {
            sender.sendRichMessage(I18n.trans("feedback.error-non-player"));
            return true;
        }
        if (BirthdayStorage.hasWithdrawnThisYear(sender.getName())) {
            sender.sendRichMessage(I18n.trans("feedback.error-has-withdrawn-this-year"));
            return true;
        }
        MonthDay birthday = BirthdayStorage.getBirthday(sender.getName());
        if (birthday == null) {
            sender.sendRichMessage(I18n.trans("feedback.error-birthday-not-set-yet"));
            return true;
        }
        if (!BirthdayStorage.canWithdrawToday(sender.getName())) {
            sender.sendRichMessage(I18n.trans("feedback.error-not-birthday-today", birthday.getMonthValue(), birthday.getDayOfMonth()));
            return true;
        }

        BirthdayStorage.markWithdrawnThisYear(sender.getName(), true);
        BirthdayStorage.saveBirthdayConfig();

        ItemStack present = Birthday70Cents.getInstance().getPresent();
        ItemUtils.giveOrDrop((Player) sender, present);
        sender.sendRichMessage(I18n.trans("feedback.withdraw-success"));
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
