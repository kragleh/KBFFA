package com.kragleh.kbffa.arena;

import com.kragleh.kbffa.KBFFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class Arena {

    private String name;
    private Location spawn;
    private int maxY;
    private World world;

    public Arena(String name) {
        YamlConfiguration arenas = KBFFA.getArenas();
        this.name = name;
        double x = arenas.getDouble("arenas." + name + ".spawn.x");
        double y = arenas.getDouble("arenas." + name + ".spawn.y");
        double z = arenas.getDouble("arenas." + name + ".spawn.z");
        this.maxY = arenas.getInt("arenas." + name + ".y.max");
        this.world = Bukkit.getWorld(arenas.getString("arenas." + name + ".world"));
        this.spawn = new Location(world, x, y, z, 0, 0);
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
