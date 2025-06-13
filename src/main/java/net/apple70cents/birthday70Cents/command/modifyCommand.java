package net.apple70cents.birthday70Cents.command;

import net.apple70cents.birthday70Cents.util.BirthdayStorage;
import net.apple70cents.birthday70Cents.util.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.MonthDay;
import java.util.List;

public class modifyCommand {
    public boolean work(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("birthday.admin.modify")) return false;
        String targetPlayer = args.length > 1 ? args[1] : "";
        String monthDay = args.length > 2 ? args[2] : "";
        boolean resetYearlyWithdrawnStatus = args.length > 3 ? Boolean.parseBoolean(args[3]) : true;
        MonthDay result = BirthdayStorage.parseBirthday(monthDay);
        if (result == null) {
            sender.sendRichMessage(I18n.trans("feedback.error-invalid-date"));
            return true;
        }
        MonthDay oldBirthday = BirthdayStorage.getBirthday(targetPlayer);
        BirthdayStorage.setBirthday(targetPlayer, result.getMonthValue(), result.getDayOfMonth());
        if (resetYearlyWithdrawnStatus) {
            BirthdayStorage.markWithdrawnThisYear(targetPlayer, false);
        }
        BirthdayStorage.saveBirthdayConfig();
        sender.sendRichMessage(I18n.trans("feedback.modify-success", targetPlayer, result.getMonthValue(), result.getDayOfMonth(),
                oldBirthday == null ? "X" : (oldBirthday.getMonthValue() + "-" + oldBirthday.getDayOfMonth())));
        return true;
    }

    public @Nullable List<String> getTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 2) {
            return BirthdayStorage.getRecordedNames();
        } else if (args.length == 3) {
            try {
                return List.of("MM-dd".substring(Math.min(args[2].length(), 5)));
            } catch (Exception e) {
                return List.of("MM-dd");
            }
        } else if (args.length == 4) {
            return List.of("true", "false");
        }
        return List.of("");
    }
}
