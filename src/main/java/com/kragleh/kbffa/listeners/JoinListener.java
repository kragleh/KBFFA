package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(ArenaManager.getCurrent().getSpawn());
        player.getActivePotionEffects().forEach(potionEffect -> {
            player.removePotionEffect(potionEffect.getType());
        });
        player.getInventory().clear();
    }

}
