package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.util.BirthdayStorage;
import net.apple70cents.birthday70Cents.util.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.MonthDay;
import java.util.List;

public class setCommand{
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("birthday.basic.set")) return false;
        if (!(sender instanceof Player)) {
            sender.sendRichMessage(I18n.trans("feedback.error-non-player"));
            return true;
        }
        String inputString = args.length > 1 ? args[1] : "";
        MonthDay result = BirthdayStorage.parseBirthday(inputString);
        if (result == null) {
            sender.sendRichMessage(I18n.trans("feedback.error-invalid-date"));
            return true;
        }
        MonthDay currentBirthday = BirthdayStorage.getBirthday(sender.getName());
        if (currentBirthday != null) {
            sender.sendRichMessage(I18n.trans("feedback.error-has-set-birthday", currentBirthday.getMonthValue(), currentBirthday.getDayOfMonth()));
            return true;
        }
        BirthdayStorage.setBirthday(sender.getName(), result.getMonthValue(), result.getDayOfMonth());
        BirthdayStorage.saveBirthdayConfig();
        sender.sendRichMessage(I18n.trans("feedback.set-success", result.getMonthValue(), result.getDayOfMonth()));
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        try {
            return List.of("MM-dd".substring(Math.min(args[1].length(), 5)));
        } catch (Exception e) {
            return List.of("MM-dd");
        }
    }
}
