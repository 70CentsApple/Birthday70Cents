package net.apple70cents.birthday70Cents.util;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BirthdayStorage {
    private static File file;
    private static YamlConfiguration birthdayConfig;

    public static void init() {
        file = new File(Birthday70Cents.getInstance().getDataFolder(), "birthday.yml");
        if (!file.exists()) {
            Birthday70Cents.getInstance().getDataFolder().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        birthdayConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static void setBirthday(String playerName, int month, int day) {
        UUID uuid = getUuidByName(playerName);
        String key = uuid.toString();
        birthdayConfig.set(key + ".name", playerName);
        birthdayConfig.set(key + ".birthday.month", month);
        birthdayConfig.set(key + ".birthday.day", day);
        if (!birthdayConfig.contains(key + ".withdrawn-years")) {
            birthdayConfig.set(key + ".withdrawn-years", new ArrayList<Integer>());
        }

        saveBirthdayConfig();
    }

    public static MonthDay getBirthday(String playerName) {
        UUID uuid = getUuidByName(playerName);
        String key = uuid.toString();
        if (!birthdayConfig.contains(key + ".birthday")) return null;
        int month = birthdayConfig.getInt(key + ".birthday.month", -1);
        int day = birthdayConfig.getInt(key + ".birthday.day", -1);
        if (month < 1 || day < 1) return null;
        return MonthDay.of(month, day);
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> getWithdrawnYears(String playerName) {
        UUID uuid = getUuidByName(playerName);
        List<Integer> list = (List<Integer>) birthdayConfig.getList(uuid + ".withdrawn-years");
        return list != null ? list : new ArrayList<>();
    }

    public static boolean hasWithdrawnThisYear(String playerName) {
        int year = java.time.LocalDate.now().getYear();
        return getWithdrawnYears(playerName).contains(year);
    }

    public static boolean canWithdrawToday(String playerName) {
        MonthDay birthday = getBirthday(playerName);
        if (birthday == null) return false;

        int leapYearAdjustment = Config.getInstance().getInt("adjust-leap-year");
        if (!java.time.LocalDate.now().isLeapYear() && MonthDay.of(2,29).equals(birthday)){
            switch (leapYearAdjustment) {
                case 0:
                    break;
                case 1:
                    birthday = MonthDay.of(3,1);
                    break;
                case -1:
                    birthday = MonthDay.of(2, 28);
                    break;
            }
        }

        return MonthDay.now().equals(birthday);
    }

    @SuppressWarnings("unchecked")
    public static void markWithdrawnThisYear(String playerName, boolean status) {
        int year = java.time.LocalDate.now().getYear();
        UUID uuid = getUuidByName(playerName);
        String key = uuid + ".withdrawn-years";
        Set<Integer> years = new HashSet<>((List<Integer>) birthdayConfig.getList(key, new ArrayList<Integer>()));
        if (status) {
            years.add(year);
        } else {
            years.remove(year);
        }
        birthdayConfig.set(key, years.stream().toList());
    }

    public static UUID getUuidByName(String name) {
        Player online = Bukkit.getPlayerExact(name);
        if (online != null) {
            return online.getUniqueId();
        }

        OfflinePlayer offline = Bukkit.getOfflinePlayer(name);
        return offline.getUniqueId();
    }

    public static List<String> getRecordedNames() {
        List<String> names = new ArrayList<>();
        for (String key : birthdayConfig.getKeys(false)) {
            String name = birthdayConfig.getString(key + ".name");
            if (name != null) {
                names.add(name);
            }
        }
        return names;
    }

    public static MonthDay parseBirthday(String input) {
        if (input == null) {
            return null;
        }

        try {
            return MonthDay.parse(input, DateTimeFormatter.ofPattern("MM-dd"));
        } catch (DateTimeException ex) {
            try {
                return MonthDay.parse("0" + input, DateTimeFormatter.ofPattern("MM-dd"));
            } catch (DateTimeException ex2) {
                return null;
            }
        }
    }

    public static void saveBirthdayConfig() {
        try {
            birthdayConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
