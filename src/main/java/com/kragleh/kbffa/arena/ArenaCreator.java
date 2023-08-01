package com.kragleh.kbffa.arena;

import com.kragleh.kbffa.KBFFA;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class ArenaCreator {

    private String name;
    private Location spawn;
    private int maxY;
    private int minY;
    private World world;

    public ArenaCreator() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void create() {
        YamlConfiguration arenas = KBFFA.getArenas();
        arenas.set("arenas." + name + ".spawn", spawn);
        arenas.set("arenas." + name + ".y.min", minY);
        arenas.set("arenas." + name + ".y.max", maxY);
        arenas.set("arenas." + name + ".world", world.getName());
        KBFFA.saveArenas();

        YamlConfiguration worlds = KBFFA.getWorlds();
        List<String> list = worlds.getStringList("worlds");
        if (list.contains(world.getName())) return;
        list.add(world.getName());
        worlds.set("worlds", list);
        KBFFA.saveArenas();
    }

}
