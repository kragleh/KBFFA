package com.kragleh.kbffa.util;

import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.db.PlayerStorage;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PAPIExpansion extends PlaceholderExpansion {

    private final KBFFA plugin;

    public PAPIExpansion(KBFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "kragleh";
    }

    @Override
    public String getIdentifier() {
        return "kbffa";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "KBFFA";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("kills")) {
            return String.valueOf(PlayerStorage.getKills(player.getPlayer()));
        }

        if (params.equalsIgnoreCase("deaths")) {
            return String.valueOf(PlayerStorage.getDeaths(player.getPlayer()));
        }

        if (params.equalsIgnoreCase("rampage")) {
            return String.valueOf(PlayerStorage.getRampage(player.getPlayer()));
        }

        if (params.equalsIgnoreCase("kit")) {
            return String.valueOf(PlayerStorage.getKit(player.getPlayer()));
        }

        return null; // Placeholder is unknown by the expansion
    }
}
