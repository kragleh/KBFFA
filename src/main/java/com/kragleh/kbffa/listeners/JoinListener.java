package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.db.PlayerStorage;
import com.kragleh.kbffa.util.KitUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!PlayerStorage.create(player)) {
            player.kickPlayer("There was an issue checking your data!");
        }

        player.teleport(ArenaManager.getCurrent().getSpawn());
        player.getActivePotionEffects().forEach(potionEffect -> {
            player.removePotionEffect(potionEffect.getType());
        });
        KitUtil.giveKit(player);
    }

}
