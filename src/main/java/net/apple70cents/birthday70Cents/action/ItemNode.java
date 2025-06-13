package net.apple70cents.birthday70Cents.action;

import net.apple70cents.birthday70Cents.util.ItemUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemNode implements ActionNode {
    private final ItemStack item;

    public ItemStack getItem() {
        return item;
    }

    public ItemNode(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(Player player) {
        ItemUtils.giveOrDrop(player, item.clone());
    }

    @Override
    public ConfigurationSection serialize(ConfigurationSection section) {
        section.set("type", "item");
        section.set("item", item);
        return section;
    }

    public static ActionNode deserialize(ConfigurationSection section) {
        ItemStack item = section.getItemStack("item");
        return new ItemNode(item);
    }
}
