package net.apple70cents.birthday70Cents.constant;

import net.apple70cents.birthday70Cents.util.I18n;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class GuiItems {
    public static final ItemStack SEPARATOR_PANE;

    static {
        SEPARATOR_PANE = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        SEPARATOR_PANE.editMeta(meta -> {
            meta.displayName(Component.text("🍏"));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack COMMAND_NODE;

    static {
        COMMAND_NODE = new ItemStack(Material.OAK_SIGN);
        COMMAND_NODE.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }

    public static final ItemStack SORT_BUTTON;

    static {
        SORT_BUTTON = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        SORT_BUTTON.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.sort"));
            meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.sort-lore-1"),
                    I18n.transAsRichComponent("gui.buttons.sort-lore-2"),
                    I18n.transAsRichComponent("gui.buttons.sort-lore-3")));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack GET_ITEM_NODE_BUTTON;

    static {
        GET_ITEM_NODE_BUTTON = new ItemStack(Material.APPLE);
        GET_ITEM_NODE_BUTTON.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.get-item-node"));
            meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.get-item-node-lore")));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack GET_COMMAND_NODE_BUTTON;

    static {
        GET_COMMAND_NODE_BUTTON = new ItemStack(Material.DARK_OAK_SIGN);
        GET_COMMAND_NODE_BUTTON.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.get-command-node"));
            meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.get-command-node-lore")));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack ITEM_NODE_EDIT_TIPS;

    static {
        ITEM_NODE_EDIT_TIPS = new ItemStack(Material.CRAFTING_TABLE);
        ITEM_NODE_EDIT_TIPS.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.item-node-edit-tips"));
            meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.item-node-edit-tips-lore")));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack COMMAND_NODE_EDIT_COMMAND;

    static {
        COMMAND_NODE_EDIT_COMMAND = new ItemStack(Material.OAK_SIGN);
        COMMAND_NODE_EDIT_COMMAND.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.command-node-edit-command"));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack COMMAND_NODE_EDIT_AS_CONSOLE;

    static {
        COMMAND_NODE_EDIT_AS_CONSOLE = new ItemStack(Material.OAK_SIGN);
        COMMAND_NODE_EDIT_AS_CONSOLE.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.command-node-edit-as-console"));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }

    public static final ItemStack PRESENT_ITEM_TIPS;

    static {
        PRESENT_ITEM_TIPS = new ItemStack(Material.ARROW);
        PRESENT_ITEM_TIPS.editMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.displayName(I18n.transAsRichComponent("gui.buttons.present-item-tips"));
            meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.present-item-tips-lore-1"),
                    I18n.transAsRichComponent("gui.buttons.present-item-tips-lore-2")));
            meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
        });
    }


}
