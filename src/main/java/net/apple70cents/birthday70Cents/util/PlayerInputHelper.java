package net.apple70cents.birthday70Cents.util;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.apple70cents.birthday70Cents.Birthday70Cents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class PlayerInputHelper {
    private static final Map<UUID, InputSession> activeSessions = new ConcurrentHashMap<>();
    private static Listener listener;

    public static void shutdown() {
        activeSessions.keySet().forEach(PlayerInputHelper::cancelSession);
        activeSessions.clear();
        if (listener != null) {
            HandlerList.unregisterAll(listener);
            listener = null;
        }
    }

    public static boolean hasInputRequest(Player player) {
        return activeSessions.containsKey(player.getUniqueId());
    }

    public static void promptInput(Player player, Component prompt, Consumer<String> callback, int timeout) {
        UUID uuid = player.getUniqueId();
        cancelSession(uuid);
        player.sendMessage(prompt);
        InputSession session = new InputSession(callback);
        activeSessions.put(uuid, session);

        Birthday70Cents plugin = Birthday70Cents.getInstance();
        player.getScheduler().run(plugin, task1 -> {
            session.timeoutTask = player.getScheduler().runDelayed(plugin, task -> {
                InputSession expiredSession = activeSessions.remove(uuid);
                if (expiredSession != null) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null && p.isOnline()) {
                        p.sendMessage(I18n.transAsRichComponent("feedback.error-timeout"));
                    }
                }
            }, () -> {}, timeout * 20L);
        }, () -> {
        });
    }

    public static void registerListener() {
        listener = new Listener() {
            @EventHandler(priority = EventPriority.LOWEST)
            public void onChat(AsyncPlayerChatEvent event) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                InputSession session = activeSessions.get(uuid);

                if (session != null) {
                    event.setCancelled(true);
                    String input = event.getMessage().trim();
                    Birthday70Cents.getInstance().getServer().getAsyncScheduler()
                                   .runNow(Birthday70Cents.getInstance(), task -> {
                                       InputSession removed = activeSessions.remove(uuid);
                                       if (removed != null) {
                                           removed.callback.accept(input);
                                       }
                                   });
                }
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent event) {
                cancelSession(event.getPlayer().getUniqueId());
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, Birthday70Cents.getInstance());
    }

    private static void cancelSession(UUID uuid) {
        InputSession session = activeSessions.remove(uuid);
        if (session != null && session.timeoutTask != null) {
            session.timeoutTask.cancel();
        }
    }

    private static class InputSession {
        final Consumer<String> callback;
        ScheduledTask timeoutTask;

        InputSession(Consumer<String> callback) {
            this.callback = callback;
        }
    }
}