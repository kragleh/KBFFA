package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.arena.Arena;
import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getY() <= ArenaManager.getCurrent().getMaxY()) {
            event.getPlayer().setHealth(0);
        }
    }

}
