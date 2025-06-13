package net.apple70cents.birthday70Cents.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemUtils {
    public static void giveOrDrop(Player player, ItemStack item) {
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);

        if (!leftover.isEmpty()) {
            World world = player.getWorld();
            Location loc = player.getLocation();
            leftover.values().forEach(stack -> {
                world.dropItem(loc, stack);
            });
        }
    }
}
