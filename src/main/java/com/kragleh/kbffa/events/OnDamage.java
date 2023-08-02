package com.kragleh.kbffa.events;

import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (ArenaManager.getCurrent().getMaxY() < event.getEntity().getLocation().getY()) {
            event.setDamage(0);
            event.setCancelled(true);
        } else if (0 > event.getEntity().getLocation().getY()) {
            ((Player) event.getEntity()).setHealth(0);
        } else {
            event.setDamage(0);
        }
    }

}

