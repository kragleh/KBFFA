package com.kragleh.kbffa.events;

import com.kragleh.kbffa.arena.Arena;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.util.KitUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(ArenaManager.getCurrent().getSpawn());
        KitUtil.giveKit(event.getPlayer());
    }

}
