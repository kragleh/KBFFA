package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.util.MessageUtil;
import com.kragleh.kbffa.util.RespawnUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Random r = new Random();
        Player player = event.getEntity();
        Player killer = player.getKiller();

        event.setDeathMessage(null);
        event.getDrops().clear();

        if (killer == null) {
            List<String> messages = KBFFA.getMessages().getStringList("game.slipped");
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            messages.get(
                                    r.nextInt(messages.size())))
                                            .replace("%player%", event.getEntity().getName()));
        } else {
            List<String> messages = KBFFA.getMessages().getStringList("game.killed");
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            messages.get(
                                    r.nextInt(messages.size())))
                                            .replace("%killer%", killer.getName())
                                            .replace("%player%", event.getEntity().getName()));
        }

        RespawnUtil.respawn(player);
    }

}
