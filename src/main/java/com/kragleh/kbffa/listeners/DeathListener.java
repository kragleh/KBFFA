package com.kragleh.kbffa.listeners;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.db.PlayerStorage;
import com.kragleh.kbffa.rampage.RampageManager;
import com.kragleh.kbffa.util.KitUtil;
import com.kragleh.kbffa.util.MessageUtil;
import com.kragleh.kbffa.util.RespawnUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Random r = new Random();
        Player player = event.getEntity();
        Player killer = player.getKiller();

        event.setDeathMessage(null);
        event.getDrops().clear();

        RampageManager.removeRampage(player);

        if (killer == null) {
            List<String> messages = KBFFA.getMessages().getStringList("game.slipped");
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            messages.get(
                                    r.nextInt(messages.size())))
                                            .replace("%player%", event.getEntity().getName()));
        } else {

            RampageManager.getRampage(killer);

            PlayerInventory inv = killer.getInventory();
            int pearlSlot = PlayerStorage.getPearl(killer);
            ItemStack pearl = inv.getItem(pearlSlot);

            if (pearl == null || pearl.getType() != Material.ENDER_PEARL) {
                pearl = new ItemStack(Material.ENDER_PEARL);
                inv.setItem(pearlSlot, pearl);
            } else {
                pearl.setAmount(pearl.getAmount() + 1);
            }

            ItemStack blocks = inv.getItem(1);

            if (blocks == null) {
                blocks = new ItemStack(Material.valueOf(KBFFA.getKits().getString("kits." + PlayerStorage.getKit(killer) + ".block")));
                blocks.setAmount(12);
                inv.setItem(1, blocks);
            } else {
                if (blocks.getAmount() > 52) {
                    blocks.setAmount(blocks.getAmount() + 12);
                }
            }

            PlayerStorage.addKill(killer);

            List<String> messages = KBFFA.getMessages().getStringList("game.killed");
            Bukkit.broadcastMessage(
                    MessageUtil.format(
                            messages.get(
                                    r.nextInt(messages.size())))
                                            .replace("%killer%", killer.getName())
                                            .replace("%player%", event.getEntity().getName()));
        }

        PlayerStorage.addDeath(player);
        RespawnUtil.respawn(player);
    }

}
