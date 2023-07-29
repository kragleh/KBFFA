package com.kragleh.kbffa.arena;

import com.kragleh.kbffa.util.VoidChunkGenerator;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public class Arena {

    private String name;
    private Location spawn;
    private int maxY;
    private int minY;
    private World world;

    public Arena(String name, YamlConfiguration arenas) {
        this.name = name;
        this.spawn = arenas.getLocation(name + ".spawn");
        this.maxY = arenas.getInt(name + ".y.max");
        this.minY = arenas.getInt(name + ".y.min");
        WorldCreator worldCreator = new WorldCreator(Objects.requireNonNull(arenas.getString(name + ".world")));
        worldCreator.generator(new VoidChunkGenerator());
        worldCreator.generateStructures(false);
        world = worldCreator.createWorld();
    }

    public String getName() {
        return name;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }
}
