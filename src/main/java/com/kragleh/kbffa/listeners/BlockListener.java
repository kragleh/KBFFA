package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (ArenaManager.getCurrent().getMaxY() < event.getBlock().getY()) {
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
