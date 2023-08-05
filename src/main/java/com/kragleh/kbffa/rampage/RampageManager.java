package com.kragleh.kbffa.rampage;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class RampageManager {

    private static HashMap<Player, Integer> map = new HashMap<>();

    public static void addRampage(Player player) {
        if (map.containsKey(player)) {
            int r = map.get(player);
            map.remove(player);
            map.put(player, (r + 1));
        } else {
            map.put(player, 1);
        }
    }

    public static void removeRampage(Player player) {
        map.remove(player);
    }

    public static int getRampage(Player player) {
        if (map.get(player) == null) {
            return 0;
        }

        return map.get(player);
    }

}
