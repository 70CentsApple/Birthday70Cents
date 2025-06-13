package net.apple70cents.birthday70Cents.gui;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.action.ActionNode;
import net.apple70cents.birthday70Cents.action.CommandNode;
import net.apple70cents.birthday70Cents.action.ItemNode;
import net.apple70cents.birthday70Cents.constant.GuiItems;
import net.apple70cents.birthday70Cents.constant.Keys;
import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.PresetManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BirthdayEditGui implements InventoryHolder {
    private Inventory inv;
    private ItemStack present;

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    public BirthdayEditGui() {
        load();
    }

    private void initItems() {
        // Separator Panes
        for (int slot = 0; slot <= 17; slot++) {
            inv.setItem(slot, GuiItems.SEPARATOR_PANE);
        }
        // Present Item Tips
        inv.setItem(3, GuiItems.PRESENT_ITEM_TIPS);
        // Present Item
        inv.setItem(4, present);
        // Sort Button
        inv.setItem(13, GuiItems.SORT_BUTTON);
        // Get Item Node Button
        inv.setItem(12, GuiItems.GET_ITEM_NODE_BUTTON);
        // Get Command Node Button
        inv.setItem(14, GuiItems.GET_COMMAND_NODE_BUTTON);

        List<ActionNode> actions = Birthday70Cents.getInstance().getActions();
        for (int i = 0; i < actions.size() && 18 + i < 54; i++) {
            int slot = 18 + i;
            ActionNode node = actions.get(i);
            ItemStack display = ItemStack.empty();

            final int idx = i;
            if (node instanceof ItemNode) {
                display = ((ItemNode) node).getItem().clone();

                display.editMeta(meta -> {
                    meta.displayName(I18n.transAsRichComponent("gui.nodes.item"));
                    meta.lore(List.of(
                            I18n.transAsRichComponent("gui.details.item-content")
                                .append(((ItemNode) node).getItem().displayName()),
                            I18n.transAsRichComponent("gui.details.item-amount", ((ItemNode) node).getItem().getAmount()),
                            I18n.transAsRichComponent("gui.details.node-trail")
                    ));
                    meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
                    meta.getPersistentDataContainer().set(Keys.NODE_INDEX, PersistentDataType.INTEGER, idx);
                });

            } else if (node instanceof CommandNode) {
                display = GuiItems.COMMAND_NODE;

                String cmd = ((CommandNode) node).getCommand();
                boolean asConsole = ((CommandNode) node).isAsConsole();

                display.editMeta(meta -> {
                    meta.displayName(I18n.transAsRichComponent("gui.nodes.command"));
                    meta.lore(List.of(
                            I18n.transAsRichComponent("gui.details.command-content", cmd),
                            I18n.transAsRichComponent("gui.details.command-as-console", asConsole),
                            I18n.transAsRichComponent("gui.details.node-trail")
                    ));
                    meta.getPersistentDataContainer().set(Keys.GUI_ITEM, PersistentDataType.INTEGER, 0x70ca);
                    meta.getPersistentDataContainer().set(Keys.NODE_INDEX, PersistentDataType.INTEGER, idx);
                });
            }
            inv.setItem(slot, display);
        }
    }

    public void load() {
        Birthday70Cents plugin = Birthday70Cents.getInstance();
        inv = plugin.getServer().createInventory(this, 9 * 6, Component.empty());
        present = plugin.getPresent();
        initItems();
    }

    public void refresh(Player player) {
        player.closeInventory();
        load();
        player.openInventory(inv);
    }

    public static boolean isRecognizedGuiItem(ItemStack item) {
        return item != null && item.getPersistentDataContainer().has(Keys.GUI_ITEM);
    }

    public void replacePresent(ItemStack newPresent) {
        present = newPresent.clone();
        inv.setItem(4, present);
        PresetManager.setPresent(present);
    }

    public ItemStack getPresent() {
        return present.clone();
    }
}
