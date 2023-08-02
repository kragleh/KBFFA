package com.kragleh.kbffa.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class HandSwitchListener implements Listener {

    @EventHandler
    public void onHandSwitch(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

}
