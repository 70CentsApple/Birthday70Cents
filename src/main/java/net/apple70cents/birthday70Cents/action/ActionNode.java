package net.apple70cents.birthday70Cents.action;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface ActionNode {
    void execute(Player player);
    ConfigurationSection serialize(ConfigurationSection section);

    static ActionNode deserialize(ConfigurationSection section) {
        String type = section.getString("type", "");
        switch (type) {
            case "item":
                return ItemNode.deserialize(section);
            case "command":
                return CommandNode.deserialize(section);
            default:
                throw new IllegalArgumentException("Unknown node type: " + type);
        }
    }
}
