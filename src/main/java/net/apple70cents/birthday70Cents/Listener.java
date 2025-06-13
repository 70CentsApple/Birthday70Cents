package net.apple70cents.birthday70Cents;

import net.apple70cents.birthday70Cents.action.ActionNode;
import net.apple70cents.birthday70Cents.action.CommandNode;
import net.apple70cents.birthday70Cents.action.ItemNode;
import net.apple70cents.birthday70Cents.constant.GuiItems;
import net.apple70cents.birthday70Cents.constant.Keys;
import net.apple70cents.birthday70Cents.gui.BirthdayEditGui;
import net.apple70cents.birthday70Cents.gui.NodeEditGui;
import net.apple70cents.birthday70Cents.util.I18n;
import net.apple70cents.birthday70Cents.util.PlayerInputHelper;
import net.apple70cents.birthday70Cents.util.PresetManager;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Listener implements org.bukkit.event.Listener {

    // Unwrap a present
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getPersistentDataContainer().has(Keys.BIRTHDAY_PRESENT)) {
            event.setCancelled(true);

            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                item.setAmount(item.getAmount() - 1);
                List<ActionNode> actions = Birthday70Cents.getInstance().getActions();
                for (ActionNode node : actions) {
                    event.getPlayer().getScheduler().run(Birthday70Cents.getInstance(),
                            task -> node.execute(event.getPlayer()), () -> {
                            });
                }
            }
        }
    }

    // GUI
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryView view = event.getView();
        if (view.getTopInventory().getHolder() instanceof BirthdayEditGui birthdayEditGui) {
            PresetManager.savePresetConfig();
        } else if (view.getTopInventory().getHolder() instanceof NodeEditGui nodeEditGui) {
            if (!PlayerInputHelper.hasInputRequest((Player) event.getPlayer())) {
                event.getPlayer().getScheduler().runDelayed(Birthday70Cents.getInstance(), task -> {
                    event.getPlayer().openInventory(new BirthdayEditGui().getInventory());
                }, () -> {
                }, 1L);
            }
        }

        // in whatever inventory the player is, if they have a cursor item that is recognized as a GUI item, clear it
        Player player = (Player) event.getPlayer();
        ItemStack cursor = player.getItemOnCursor();
        if (!cursor.getType().isAir()) {
            if (BirthdayEditGui.isRecognizedGuiItem(cursor)) {
                player.setItemOnCursor(null);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        int rawSlot = event.getRawSlot();
        if (view.getTopInventory().getHolder() instanceof BirthdayEditGui) {
            if (GuiItems.SEPARATOR_PANE.isSimilar(event.getCurrentItem()) || GuiItems.PRESENT_ITEM_TIPS.isSimilar(event.getCurrentItem())) {
                event.setCancelled(true);
                return;
            }

            // present item
            if (rawSlot == 4) {
                event.setCancelled(true);
                ItemStack cursorItem = event.getCursor();
                BirthdayEditGui holder = (BirthdayEditGui) view.getTopInventory().getHolder();

                if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                    // replace present with cursor item
                    if (cursorItem.getType() != Material.AIR && !BirthdayEditGui.isRecognizedGuiItem(cursorItem)) {
                        holder.replacePresent(cursorItem.clone());
                        PresetManager.savePresetConfig();
                    }
                } else if (event.getAction() == InventoryAction.PICKUP_HALF || event.getAction() == InventoryAction.PICKUP_ALL ||
                        event.getAction() == InventoryAction.PICKUP_SOME || event.getAction() == InventoryAction.PICKUP_ONE) {
                    event.getWhoClicked().setItemOnCursor(holder.getPresent().clone());
                }
                return;
            }

            // sort item
            if (rawSlot == 13 && event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
                event.setCancelled(true);
                sortAndSaveActions(event, view);
                ((BirthdayEditGui) view.getTopInventory().getHolder()).refresh((Player) event.getWhoClicked());
                return;
            }

            // get item node
            if (rawSlot == 12 && event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
                event.setCancelled(true);
                ItemStack itemNode = GuiItems.GET_ITEM_NODE_BUTTON.clone();
                itemNode.editMeta(meta -> {
                    meta.getPersistentDataContainer()
                        .set(Keys.NODE_INDEX, PersistentDataType.INTEGER, PresetManager.addNewDefaultItemAction());
                });
                event.getWhoClicked().setItemOnCursor(itemNode.clone().asOne());
                return;
            }

            // get command node
            if (rawSlot == 14 & event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
                event.setCancelled(true);
                ItemStack itemNode = GuiItems.GET_COMMAND_NODE_BUTTON.clone();
                itemNode.editMeta(meta -> {
                    meta.getPersistentDataContainer()
                        .set(Keys.NODE_INDEX, PersistentDataType.INTEGER, PresetManager.addNewDefaultCommandAction());
                });
                event.getWhoClicked().setItemOnCursor(itemNode.clone().asOne());
                return;
            }

            // actions
            if (rawSlot >= 18 && rawSlot < 54) {
                // Clone
                if (event.getAction() == InventoryAction.CLONE_STACK) {
                    event.setCancelled(true);
                    ItemStack node = event.getCurrentItem();
                    if (node != null) {
                        event.getWhoClicked().setItemOnCursor(node.clone().asOne());
                    }
                    return;
                }
                // Right click
                if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    ItemStack node = event.getCurrentItem();
                    if (BirthdayEditGui.isRecognizedGuiItem(node) && node.getPersistentDataContainer()
                                                                         .has(Keys.NODE_INDEX, PersistentDataType.INTEGER)) {
                        event.setCancelled(true);
                        int index = node.getPersistentDataContainer().get(Keys.NODE_INDEX, PersistentDataType.INTEGER);
                        Player player = (Player) event.getWhoClicked();
                        player.openInventory(new NodeEditGui(index).getInventory());
                        return;
                    }
                }
                // Discard
                else if (event.getAction() == InventoryAction.DROP_ALL_SLOT || event.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    ItemStack node = event.getCurrentItem();
                    if (BirthdayEditGui.isRecognizedGuiItem(node) && node.getPersistentDataContainer()
                                                                         .has(Keys.NODE_INDEX, PersistentDataType.INTEGER)) {
                        event.setCancelled(true);
                        event.getInventory().setItem(rawSlot, new ItemStack(Material.AIR));
                        sortAndSaveActions(event, view);
                        PresetManager.savePresetConfig();
                        ((BirthdayEditGui) view.getTopInventory().getHolder()).refresh((Player) event.getWhoClicked());
                        return;
                    }
                }
                // Place a node
                else if (event.getAction() == InventoryAction.PLACE_ONE || event.getAction() == InventoryAction.PLACE_SOME ||
                        event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_ALL_INTO_BUNDLE ||
                        event.getAction() == InventoryAction.PLACE_SOME_INTO_BUNDLE) {
                    ItemStack node = event.getCurrentItem();
                    if (BirthdayEditGui.isRecognizedGuiItem(node) && node.getPersistentDataContainer()
                                                                         .has(Keys.NODE_INDEX, PersistentDataType.INTEGER)) {
                        event.setCancelled(true);
                        event.getInventory().setItem(rawSlot, node.clone().asOne());
                        sortAndSaveActions(event, view);
                        PresetManager.savePresetConfig();
                        ((BirthdayEditGui) view.getTopInventory().getHolder()).refresh((Player) event.getWhoClicked());
                        return;
                    }
                }
            }

            // cancel all other actions that are not related to the gui
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                    event.getAction() == InventoryAction.COLLECT_TO_CURSOR ||
                    event.getAction() == InventoryAction.HOTBAR_SWAP ||
                    event.getAction() == InventoryAction.DROP_ALL_CURSOR ||
                    event.getAction() == InventoryAction.DROP_ALL_SLOT ||
                    event.getAction() == InventoryAction.DROP_ONE_CURSOR ||
                    event.getAction() == InventoryAction.DROP_ONE_SLOT ||
                    event.getAction() == InventoryAction.CLONE_STACK) {
                event.setCancelled(true);
                return;
            }

            // arrange node sequences only in the gui
            if (event.getAction() == InventoryAction.PLACE_ALL ||
                    event.getAction() == InventoryAction.PLACE_ONE ||
                    event.getAction() == InventoryAction.PLACE_SOME ||
                    event.getAction() == InventoryAction.PLACE_ALL_INTO_BUNDLE ||
                    event.getAction() == InventoryAction.PLACE_SOME_INTO_BUNDLE ||
                    event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                if (BirthdayEditGui.isRecognizedGuiItem(event.getCursor())) {
                    if (rawSlot < 18 || rawSlot >= 54) {
                        event.setCancelled(true);
                    }
                } else {
                    if (rawSlot >= 18 && rawSlot < 54) {
                        event.setCancelled(true);
                    }
                }
                return;
            }
        } else if (view.getTopInventory().getHolder() instanceof NodeEditGui nodeEditGui) {
            if (nodeEditGui.getActionNode() instanceof ItemNode itemNode) {
                if (rawSlot == 13) {
                    event.setCancelled(true);
                    if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                        ItemStack cursorItem = event.getCursor();
                        if (cursorItem.getType() != Material.AIR) {
                            nodeEditGui.itemNodeReplaceItem(cursorItem.clone());
                        }
                    } else if (event.getAction() == InventoryAction.PICKUP_HALF || event.getAction() == InventoryAction.PICKUP_ALL ||
                            event.getAction() == InventoryAction.PICKUP_SOME || event.getAction() == InventoryAction.PICKUP_ONE) {
                        event.getWhoClicked().setItemOnCursor(itemNode.getItem().clone());
                    }
                    ItemStack cursorItem = event.getCursor();
                    if (cursorItem.getType() != Material.AIR) {
                        nodeEditGui.itemNodeReplaceItem(cursorItem.clone());
                    }
                    return;
                }
            } else if (nodeEditGui.getActionNode() instanceof CommandNode commandNode) {
                if (rawSlot == 12 && event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
                    event.setCancelled(true);

                    Player player = (Player) event.getWhoClicked();
                    PlayerInputHelper.promptInput(player,
                            I18n.transAsRichComponent("feedback.edit-command")
                                .clickEvent(ClickEvent.suggestCommand(commandNode.getCommand())),
                            input -> {
                                if (input.equalsIgnoreCase("#end")) {
                                    player.sendMessage(I18n.transAsRichComponent("feedback.edit-command-canceled"));
                                    return;
                                }
                                nodeEditGui.commandNodeSetCommand(input);
                                player.sendMessage(I18n.transAsRichComponent("feedback.edit-command-success", input));
                                player.getScheduler().runDelayed(Birthday70Cents.getInstance(),
                                        task -> player.openInventory(nodeEditGui.getInventory()), () -> {
                                        }, 1L);
                            }, 30
                    );

                    player.closeInventory();

                    return;
                } else if (rawSlot == 14 && event.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
                    event.setCancelled(true);
                    boolean oldAsConsole = commandNode.isAsConsole();
                    nodeEditGui.commandNodeSetAsConsole(!oldAsConsole);
                    return;
                }
            }

            if (BirthdayEditGui.isRecognizedGuiItem(event.getCurrentItem())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    private static void sortAndSaveActions(InventoryClickEvent event, InventoryView view) {
        Inventory inv = event.getInventory();
        List<ActionNode> list = new ArrayList<>();

        for (int i = 18; i <= 53; i++) {
            ItemStack item = inv.getItem(i);
            if (BirthdayEditGui.isRecognizedGuiItem(item)) {
                ActionNode node = PresetManager.getActionNodeByIndex(item.getPersistentDataContainer()
                                                                         .get(Keys.NODE_INDEX, PersistentDataType.INTEGER));
                if (node != null) {
                    list.add(node);
                }
            }
        }
        PresetManager.setActions(list);
        PresetManager.savePresetConfig();
    }
}
