package com.kragleh.kbffa.util;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.db.PlayerStorage;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class KitUtil {

    public static void giveKit(Player player) {
        PlayerInventory inv = player.getInventory();
        String kit = PlayerStorage.getKit(player);
        YamlConfiguration kits = KBFFA.getKits();

        String knocker = kits.getString("kits." + kit + ".knocker");
        String block = kits.getString("kits." + kit + ".block");
        String helmet = kits.getString("kits." + kit + ".armor.helmet");
        String chestplate = kits.getString("kits." + kit + ".armor.chestplate");
        String leggings = kits.getString("kits." + kit + ".armor.leggings");
        String boots = kits.getString("kits." + kit + ".armor.boots");

        inv.clear();

        if (!Objects.equals(helmet, "none")) {
            ItemStack is = new ItemStack(Material.valueOf(helmet));
            inv.setHelmet(is);
        }

        if (!Objects.equals(chestplate, "none")) {
            ItemStack is = new ItemStack(Material.valueOf(chestplate));
            inv.setHelmet(is);
        }

        if (!Objects.equals(leggings, "none")) {
            ItemStack is = new ItemStack(Material.valueOf(leggings));
            inv.setHelmet(is);
        }

        if (!Objects.equals(boots, "none")) {
            ItemStack is = new ItemStack(Material.valueOf(boots));
            inv.setHelmet(is);
        }

        ItemStack stick = new ItemStack(Material.valueOf(knocker));
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick.setItemMeta(stickMeta);
        inv.setItem(0, stick);

        ItemStack blocks = new ItemStack(Material.valueOf(block));
        blocks.setAmount(24);
        inv.setItem(1, blocks);

        ItemStack pearl = new ItemStack(Material.ENDER_PEARL);
        inv.setItem(PlayerStorage.getPearl(player), pearl);
    }

}
