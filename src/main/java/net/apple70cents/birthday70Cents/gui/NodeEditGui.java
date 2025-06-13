package net.apple70cents.birthday70Cents.gui;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.apple70cents.birthday70Cents.action.ActionNode;
import net.apple70cents.birthday70Cents.action.CommandNode;
import net.apple70cents.birthday70Cents.action.ItemNode;
import net.apple70cents.birthday70Cents.constant.GuiItems;
import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.PresetManager;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeEditGui implements InventoryHolder {
    private final Inventory inv;
    private final int nodeIndex;

    public NodeEditGui(int nodeIndex) {
        this.nodeIndex = nodeIndex;
        inv = Birthday70Cents.getInstance().getServer().createInventory(this, 9 * 3, Component.empty());
        initItems();
    }

    private void initItems() {
        // Separator Panes
        for (int slot = 0; slot < 27; slot++) {
            inv.setItem(slot, GuiItems.SEPARATOR_PANE);
        }
        ActionNode action = PresetManager.loadSavedActions().get(nodeIndex);
        if (action instanceof ItemNode itemNode) {
            inv.setItem(4, GuiItems.ITEM_NODE_EDIT_TIPS);
            inv.setItem(13, itemNode.getItem());
        } else if (action instanceof CommandNode commandNode) {
            ItemStack editCommand = GuiItems.COMMAND_NODE_EDIT_COMMAND;
            editCommand.editMeta(meta -> {
                meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.command-node-edit-command-lore-1"),
                        I18n.transAsRichComponent("gui.buttons.command-node-edit-command-lore-2", commandNode.getCommand())));
            });
            inv.setItem(12, editCommand);
            ItemStack asConsole = GuiItems.COMMAND_NODE_EDIT_AS_CONSOLE;
            asConsole.editMeta(meta -> {
                meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.command-node-edit-as-console-lore-1"),
                        I18n.transAsRichComponent("gui.buttons.command-node-edit-as-console-lore-2", commandNode.isAsConsole())));
            });
            inv.setItem(14, asConsole);
        }
    }

    public void itemNodeReplaceItem(ItemStack item) {
        ActionNode action = getActionNode();
        if (action instanceof ItemNode) {
            action = new ItemNode(item);
            inv.setItem(13, item);
            PresetManager.setAction(nodeIndex, action);
            PresetManager.savePresetConfig();
        }
    }

    public void commandNodeSetCommand(String cmd) {
        ActionNode action = getActionNode();
        if (action instanceof CommandNode commandNode) {
            action = new CommandNode(cmd, commandNode.isAsConsole());
            ItemStack editCommand = GuiItems.COMMAND_NODE_EDIT_COMMAND;
            editCommand.editMeta(meta -> {
                meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.command-node-edit-command-lore-1"),
                        I18n.transAsRichComponent("gui.buttons.command-node-edit-command-lore-2", cmd)));
            });
            inv.setItem(12, editCommand);
            PresetManager.setAction(nodeIndex, action);
            PresetManager.savePresetConfig();
        }
    }

    public void commandNodeSetAsConsole(boolean asConsole) {
        ActionNode action = getActionNode();
        if (action instanceof CommandNode commandNode) {
            action = new CommandNode(commandNode.getCommand(), asConsole);
            ItemStack asConsoleItem = GuiItems.COMMAND_NODE_EDIT_AS_CONSOLE;
            asConsoleItem.editMeta(meta -> {
                meta.lore(List.of(I18n.transAsRichComponent("gui.buttons.command-node-edit-as-console-lore-1"),
                        I18n.transAsRichComponent("gui.buttons.command-node-edit-as-console-lore-2", asConsole)));
            });
            inv.setItem(14, asConsoleItem);
            PresetManager.setAction(nodeIndex, action);
            PresetManager.savePresetConfig();
        }
    }

    public ActionNode getActionNode() {
        return PresetManager.loadSavedActions().get(nodeIndex);
    }

    public boolean isItemNode() {
        return getActionNode() instanceof ItemNode;
    }

    public boolean isCommandNode() {
        return getActionNode() instanceof CommandNode;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
