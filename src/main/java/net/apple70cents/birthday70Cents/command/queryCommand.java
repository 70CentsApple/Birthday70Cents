package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.util.BirthdayStorage;
import net.apple70cents.birthday70Cents.util.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.MonthDay;
import java.util.List;

public class queryCommand {
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("birthday.admin.query")) return false;
        String targetPlayer = args.length > 1 ? args[1] : "";
        MonthDay result = BirthdayStorage.getBirthday(targetPlayer);
        sender.sendRichMessage(I18n.trans("feedback.query-success", targetPlayer,
                result == null ? "X" : (result.getMonthValue() + "-" + result.getDayOfMonth()), BirthdayStorage.hasWithdrawnThisYear(targetPlayer)));
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return args.length <= 2 ? BirthdayStorage.getRecordedNames() : List.of();
    }
}
