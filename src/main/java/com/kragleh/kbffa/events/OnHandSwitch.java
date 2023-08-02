package com.kragleh.kbffa.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class OnHandSwitch implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwitch(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

}
