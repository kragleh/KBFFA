package com.kragleh.kbffa.events;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.util.KitUtil;
import com.kragleh.kbffa.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getEntity().spigot().respawn();
                    KitUtil.giveKit(event.getEntity());
                    event.getEntity().teleport(ArenaManager.getCurrent().getSpawn());
                }
            }.runTaskLater(KBFFA.getPlugin(), 40);
            return;
        }

        Random r = new Random();
        List<String> messages = KBFFA.getMessages().getStringList("game.killed");
        event.setDeathMessage(
                MessageUtil.format(
                        messages.get(
                                r.nextInt(messages.size())))
                        .replace("%killer%", event.getEntity().getKiller().getName())
                        .replace("%player%", event.getEntity().getName()));

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().spigot().respawn();
                KitUtil.giveKit(event.getEntity());
                event.getEntity().teleport(ArenaManager.getCurrent().getSpawn());
            }
        }.runTaskLater(KBFFA.getPlugin(), 40);
    }

}
