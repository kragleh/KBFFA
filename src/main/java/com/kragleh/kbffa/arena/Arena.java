package com.kragleh.kbffa.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class Arena {

    private String name;
    private Location spawn;
    private int maxY;
    private World world;

    public Arena(String name, YamlConfiguration arenas) {
        this.name = name;
        this.spawn = arenas.getLocation("arenas." + name + ".spawn");
        this.maxY = arenas.getInt("arenas." + name + ".y.max");
        this.world = Bukkit.getWorld(arenas.getString("arenas." + name + ".world"));
    }

    public Location getSpawn() {
        return spawn;
    }

    public String getName() {
        return name;
    }

    public int getMaxY() {
        return maxY;
    }

    public World getWorld() {
        return world;
    }

}
