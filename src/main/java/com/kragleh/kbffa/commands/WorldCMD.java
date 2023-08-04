package com.kragleh.kbffa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.util.MessageUtil;
import com.kragleh.kbffa.util.VoidChunkGenerator;
import com.kragleh.kbffa.util.WorldUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

@CommandAlias("world|w")
public class WorldCMD extends BaseCommand {

    @Default
    @CommandPermission("kbffa.world")
    public void onDefault(Player player) {
        player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("usage")));
        KBFFA.getMessages().getStringList("world.cmd").forEach((message) -> {
            player.sendMessage(MessageUtil.format(message));
        });
    }

    @Subcommand("create")
    @CommandCompletion("<name>")
    @CommandPermission("kbffa.world.create")
    public void onCreate(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        File file = new File(Bukkit.getServer().getWorldContainer(), args[0]);
        if (file.exists()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.exists")));
            return;
        }

        World world = WorldUtil.loadWorld(args[0]);
        world.getBlockAt(0, 64, 0).setType(Material.BEDROCK);
        player.teleport(new Location(world, 0, 65, 0, 0, 0));
    }

    @Subcommand("tp")
    @CommandCompletion("<name>")
    @CommandPermission("kbffa.world.tp")
    public void onTeleport(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        File file = new File(Bukkit.getServer().getWorldContainer(), args[0]);

        if (!file.exists()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.no")));
            return;
        }

        World world = WorldUtil.loadWorld(args[0]);
        player.teleport(new Location(world, 0, 65, 0, 0, 0));
    }

    @Subcommand("add")
    @CommandCompletion("<name>")
    @CommandPermission("kbffa.world.add")
    public void onAdd(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        List<String> list = KBFFA.getWorlds().getStringList("worlds");

        if (list.contains(args[0])) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.has")));
            return;
        }

        File file = new File(Bukkit.getServer().getWorldContainer(), args[0]);

        if (!file.exists()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.no")));
            return;
        }

        list.add(args[0]);
        KBFFA.getWorlds().set("worlds", list);
        if (KBFFA.saveWorlds()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.added")));
        } else {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.failed-add")));
        }
    }

    @Subcommand("remove")
    @CommandCompletion("<name>")
    @CommandPermission("kbffa.world.remove")
    public void onRemove(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        List<String> worlds = KBFFA.getWorlds().getStringList("worlds");

        if (!worlds.contains(args[0])) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.dhave")));
            return;
        }

        worlds.remove(args[0]);
        KBFFA.getWorlds().set("worlds", worlds);
        KBFFA.saveWorlds();
        player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.removed")));
    }

}
