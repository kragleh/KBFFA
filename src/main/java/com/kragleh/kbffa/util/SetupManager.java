package com.kragleh.kbffa.util;

import com.kragleh.kbffa.arena.ArenaCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetupManager {

    private static HashMap<Player, ArenaCreator> map = new HashMap<>();

    public static ArenaCreator get(Player player) {
        return map.get(player);
    }

    public static ArenaCreator create(Player player) {
        if (map.get(player) != null) return null;
        ArenaCreator creator = new ArenaCreator();
        map.put(player, creator);
        return creator;
    }

    public static void finish(Player player) {
        map.get(player).create();
        map.remove(player);
    }

}
