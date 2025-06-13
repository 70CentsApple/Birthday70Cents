package net.apple70cents.birthday70Cents;

import net.apple70cents.birthday70Cents.action.ActionNode;
import net.apple70cents.birthday70Cents.util.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Birthday70Cents extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("birthday")).setExecutor(new CommandExecutor());
        getServer().getPluginManager().registerEvents(new Listener(), this);
        init();
    }

    @Override
    public void onDisable() {
        BirthdayStorage.saveBirthdayConfig();
        PresetManager.savePresetConfig();
        PlayerInputHelper.shutdown();
    }

    public static Birthday70Cents getInstance() {
        return getPlugin(Birthday70Cents.class);
    }

    public static void init() {
        Config.getInstance().load();
        I18n.init();
        BirthdayStorage.init();
        PresetManager.init();
        PlayerInputHelper.registerListener();
    }

    public ItemStack getPresent() {
        return PresetManager.loadSavedPresent();
    }

    public List<ActionNode> getActions() {
        return PresetManager.loadSavedActions();
    }
}
