package com.kragleh.kbffa.events;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.util.KitUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().spigot().respawn();
                KitUtil.giveKit(event.getEntity());
                event.getEntity().teleport(ArenaManager.getCurrent().getSpawn());
            }
        }.runTaskLater(KBFFA.getPlugin(), 100);
    }

}
