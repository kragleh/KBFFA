package com.kragleh.kbffa.events;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnBlockPlace implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (ArenaManager.getCurrent().getMaxY() < event.getPlayer().getLocation().getY()) {
            event.setCancelled(true);
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getBlock().setType(Material.AIR);
            }
        }.runTaskLater(KBFFA.getPlugin(), 60);
    }

}
