package com.kragleh.kbffa.util;

import org.bukkit.ChatColor;

public class MessageUtil {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
