package com.kragleh.kbffa.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KitUtil {

    public static void giveKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();
        ItemStack stick = new ItemStack(Material.STICK);
        stick.addEnchantment(Enchantment.KNOCKBACK, 3);
        ItemStack blocks = new ItemStack(Material.CLAY);
        blocks.setAmount(24);
        ItemStack pearl = new ItemStack(Material.ENDER_PEARL);
        inv.setItem(0, stick);
        inv.setItem(1, blocks);
        inv.setItem(8, pearl);
    }

}
