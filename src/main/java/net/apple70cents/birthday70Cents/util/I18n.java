package net.apple70cents.birthday70Cents.util;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class I18n {
    private static final Map<String, String> defaultMap = new HashMap<>();
    private static final Map<String, String> localeMap = new HashMap<>();

    public static void init() {
        Plugin plugin = Birthday70Cents.getInstance();
        String lang = Config.getInstance().getString("language");
        YamlConfiguration defCfg = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("lang/en_us.yml"), StandardCharsets.UTF_8));
        YamlConfiguration locCfg = YamlConfiguration.loadConfiguration(
            new InputStreamReader(Objects.requireNonNullElse(
                plugin.getResource("lang/" + lang + ".yml"),
                plugin.getResource("lang/en_us.yml")
            ), StandardCharsets.UTF_8)
        );

        for (String key : defCfg.getKeys(true)) {
            defaultMap.put(key, defCfg.getString(key));
        }
        for (String key : locCfg.getKeys(true)) {
            String v = locCfg.getString(key);
            if (v != null && !v.isEmpty()) {
                localeMap.put(key, v);
            }
        }
    }

    public static String trans(String key, Object... args) {
        String raw = localeMap.getOrDefault(key, defaultMap.getOrDefault(key, key));
        // replaces %0%, %1%, ...
        for (int i = 0; i < args.length; i++) {
            raw = raw.replace("%" + i + "%", args[i].toString());
        }
        return raw;
    }
    public static Component transAsRichComponent(String key, Object... args) {
        return MiniMessage.miniMessage().deserialize(trans(key, args));
    }
}