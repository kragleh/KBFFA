package com.kragleh.kbffa.arena;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    public static List<Arena> arenas = new ArrayList<>();
    private static Arena current;

    public static void load(YamlConfiguration arenas) {

    }

    public static void setCurrent(Arena arena) {
        current = arena;
    }

    public static Arena getCurrent() {
        return current;
    }

}
