package net.apple70cents.birthday70Cents.action;

import net.apple70cents.birthday70Cents.Birthday70Cents;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CommandNode implements ActionNode {
    private final String command;
    private final boolean asConsole;

    public String getCommand() {
        return command;
    }

    public boolean isAsConsole() {
        return asConsole;
    }


    public CommandNode(String command, boolean asConsole) {
        this.command = command;
        this.asConsole = asConsole;
    }

    @Override
    public void execute(Player player) {
        Birthday70Cents plugin = Birthday70Cents.getInstance();
        player.getScheduler().run(plugin, task -> {
            String cmd = command.replace("%player%", player.getName());
            if (asConsole) {
                plugin.getServer().getGlobalRegionScheduler().execute(plugin, () -> {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd);
                });
            } else {
                plugin.getServer().getRegionScheduler().execute(plugin, player.getLocation(), () -> {
                    plugin.getServer().dispatchCommand(player, cmd);
                });
            }
        }, () -> {
        });
    }

    @Override
    public ConfigurationSection serialize(ConfigurationSection section) {
        section.set("type", "command");
        section.set("command", command);
        section.set("asConsole", asConsole);
        return section;
    }

    public static ActionNode deserialize(ConfigurationSection section) {
        String cmd = section.getString("command", "");
        boolean console = section.getBoolean("asConsole", false);
        return new CommandNode(cmd, console);
    }
}

