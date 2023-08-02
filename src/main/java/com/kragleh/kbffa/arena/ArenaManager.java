package com.kragleh.kbffa.arena;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaManager {

    public static List<Arena> arenas = new ArrayList<>();
    private static Arena current;
    private static boolean initiated = false;

    public static void init(YamlConfiguration arenasConfig) {
        if (initiated) return;
        ConfigurationSection arenasSection = arenasConfig.getConfigurationSection("arenas");

        for (String arenaName : arenasSection.getKeys(false)) {
            arenas.add(new Arena(arenaName));
        }

        Random r = new Random();
        int startArena = r.nextInt(arenas.size());
        current = arenas.get(startArena);
    }

    public static void setCurrent(Arena arena) {
        current = arena;
    }

    public static Arena getCurrent() {
        return current;
    }

}
