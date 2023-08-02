package com.kragleh.kbffa.util;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnUtil {

    public static void respawn(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.teleport(ArenaManager.getCurrent().getSpawn());
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                player.getInventory().clear();
                KitUtil.giveKit(player);
            }
        }.runTaskLater(KBFFA.getPlugin(), 5);
    }

}
