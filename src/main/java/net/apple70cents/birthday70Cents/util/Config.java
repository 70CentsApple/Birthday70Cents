package net.apple70cents.birthday70Cents.util;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Config {
    private final static Config instance = new Config();

    private File file;
    private YamlConfiguration config;

    private Config() {
    }

    public void load() {
        file = new File(Birthday70Cents.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) {
            Birthday70Cents.getInstance().saveResource("config.yml", false);
        }

        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Birthday70Cents
                .getInstance().getResource("config.yml"), StandardCharsets.UTF_8));
        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);
        config.setDefaults(defaultConfig);
        config.options().copyDefaults(true);

        // The I18n might have not yet been initialized here...
        Birthday70Cents.getInstance().getLogger().info("feedback.reload".equals(I18n.trans("feedback.reload")) ? "Reloaded!" : I18n.trans("feedback.reload"));

        try {
            config.load(file);
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return config.getString(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }

    public static Config getInstance() {
        return instance;
    }
}
