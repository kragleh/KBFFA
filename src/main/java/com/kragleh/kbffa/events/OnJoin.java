package com.kragleh.kbffa.events;

import com.kragleh.kbffa.arena.Arena;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.util.KitUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Arena current = ArenaManager.getCurrent();
        event.getPlayer().teleport(current.getSpawn());
        KitUtil.giveKit(event.getPlayer());
    }

}
