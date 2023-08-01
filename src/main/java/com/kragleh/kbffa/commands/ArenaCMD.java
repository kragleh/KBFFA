package com.kragleh.kbffa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.util.MessageUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("arena|a")
public class ArenaCMD extends BaseCommand {

    @Default
    @CommandPermission("kbffa.arena")
    public void onDefault(Player player) {
        player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("usage")));
        KBFFA.getMessages().getStringList("arena.cmd").forEach((message) -> {
            player.sendMessage(MessageUtil.format(message));
        });
    }

    @Subcommand("create")
    @CommandCompletion("<name> <maxY>")
    @CommandPermission("kbffa.arena.create")
    public void onCreate(Player player, String[] args) {
        if (args.length > 3 || args.length <= 2) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        String name = args[0];
        int maxY;

        try {
            maxY = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arena.maxy-err")));
            return;
        }

        YamlConfiguration arenas = KBFFA.getArenas();

        if (arenas.getLocation("arenas." + name + ".spawn") != null) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arena.exists")));
            return;
        }

        arenas.set("arenas." + name + ".spawn", player.getLocation());
        arenas.set("arenas." + name + ".y.max", maxY);
        arenas.set("arenas." + name + ".world", name);
    }

}
