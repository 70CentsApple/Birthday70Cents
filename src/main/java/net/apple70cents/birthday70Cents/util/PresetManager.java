package net.apple70cents.birthday70Cents.util;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.action.ActionNode;
import net.apple70cents.birthday70Cents.action.CommandNode;
import net.apple70cents.birthday70Cents.action.ItemNode;
import net.apple70cents.birthday70Cents.constant.Keys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PresetManager {
    private static File file;
    private static YamlConfiguration presetConfig;

    public static void init() {
        file = new File(Birthday70Cents.getInstance().getDataFolder(), "preset.yml");
        if (!file.exists()) {
            Birthday70Cents.getInstance().getDataFolder().mkdirs();
            saveDefaultPreset();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        presetConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveDefaultPreset() {
        YamlConfiguration defaultPresetConfig = new YamlConfiguration();
        defaultPresetConfig.set("present", getDefaultPresent());
        List<ActionNode> defaultActions = getDefaultActions();
        for (int i = 0; i < defaultActions.size(); i++) {
            ConfigurationSection nodeSection = defaultPresetConfig.createSection("actions." + i);
            defaultActions.get(i).serialize(nodeSection);
        }

        try {
            defaultPresetConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePresetConfig() {
        try {
            presetConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPresent(ItemStack present) {
        presetConfig.set("present", present);
    }

    public static void setActions(List<ActionNode> actions) {
        presetConfig.set("actions", null);
        actions.removeIf(Objects::isNull);
        for (int i = 0; i < actions.size(); i++) {
            ConfigurationSection nodeSection = presetConfig.createSection("actions." + i);
            actions.get(i).serialize(nodeSection);
        }
    }

    public static int addNewDefaultItemAction() {
        List<ActionNode> actions = loadSavedActions();
        ActionNode newNode = new ItemNode(new ItemStack(Material.GOLDEN_APPLE));
        actions.add(newNode);
        setActions(actions);
        savePresetConfig();
        return actions.size() - 1;
    }

    public static int addNewDefaultCommandAction() {
        List<ActionNode> actions = loadSavedActions();
        ActionNode newNode = new CommandNode("say Hello, %player%!", true);
        actions.add(newNode);
        setActions(actions);
        savePresetConfig();
        return actions.size() - 1;
    }

    public static ActionNode getActionNodeByIndex(int idx) {
        return loadSavedActions().get(idx);
    }

    public static void setAction(int idx, ActionNode action) {
        List<ActionNode> actions = PresetManager.loadSavedActions();
        actions.set(idx, action);
        PresetManager.setActions(actions);
    }

    public static ItemStack loadSavedPresent() {
        ItemStack item = presetConfig.getItemStack("present");
        if (item == null) return null;
        item.editMeta((meta) -> {
            meta.getPersistentDataContainer().set(Keys.BIRTHDAY_PRESENT, PersistentDataType.INTEGER, 0x70ca);
        });
        return item.clone();
    }

    public static List<ActionNode> loadSavedActions() {
        List<ActionNode> chain = new ArrayList<>();
        ConfigurationSection actionsSec = presetConfig.getConfigurationSection("actions");
        if (actionsSec == null) return chain;

        Set<String> keys = actionsSec.getKeys(false);
        keys.stream()
            .sorted(Comparator.comparingInt(Integer::parseInt))
            .forEach(key -> {
                ConfigurationSection nodeSec = actionsSec.getConfigurationSection(key);
                chain.add(ActionNode.deserialize(nodeSec));
            });
        return chain;
    }

    public static ItemStack getDefaultPresent() {
        ItemStack item = new ItemStack(Material.LILY_OF_THE_VALLEY);
        item.lore(List.of(Component.text("Right click to unwrap it!").color(TextColor.color(219, 92, 92))));
        item.editMeta(meta -> {
            meta.itemName(Component.text("Birthday Present").color(TextColor.color(83, 134, 238)));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
        return item;
    }

    public static List<ActionNode> getDefaultActions() {
        ItemStack cake = new ItemStack(Material.CAKE);
        cake.editMeta(meta -> {
            meta.displayName(MiniMessage.miniMessage().deserialize("<rainbow>Is it a lie?</rainbow>"));
        });
        return List.of(
                new CommandNode("tellraw %player% {\"text\":\"❤\",\"color\":\"red\"}", true),
                new CommandNode("execute at %player% run summon minecraft:firework_rocket ~ ~2 ~", true),
                new ItemNode(cake)
        );
    }
}