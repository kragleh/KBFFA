package com.kragleh.kbffa.events;

import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.util.KitUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerDeathEvent event) {
        event.getEntity().spigot().respawn();
        KitUtil.giveKit(event.getEntity());
        event.getEntity().teleport(ArenaManager.getCurrent().getSpawn());
    }

}
