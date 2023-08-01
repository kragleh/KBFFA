package com.kragleh.kbffa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.kragleh.kbffa.KBFFA;
import com.kragleh.kbffa.util.MessageUtil;
import com.kragleh.kbffa.util.VoidChunkGenerator;
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
    @CommandPermission("kbffa.world.create")
    public void onCreate(Player player, String[] args) {
        if (args.length > 2 || args.length == 0) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("arg-err")));
            return;
        }

        File file = new File(Bukkit.getServer().getWorldContainer(), args[0]);
        if (file.exists()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.exists")));
            return;
        }

        World.Environment environment = World.Environment.NORMAL;

        if (args.length == 2) {
            try {
                environment = World.Environment.valueOf(args[1]);
            } catch (Exception e) {
                player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.types")));
                return;
            }
        }

        WorldCreator worldCreator = new WorldCreator(args[0]);
        worldCreator.generator(new VoidChunkGenerator());
        worldCreator.generateStructures(false);
        worldCreator.environment(environment);

        World world = worldCreator.createWorld();
        world.getBlockAt(0, 64, 0).setType(Material.BEDROCK);
        player.teleport(new Location(world, 0, 65, 0, 0, 0));
    }

    @Subcommand("tp")
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

        World world = Bukkit.getWorld(args[0]);

        if (world == null) {
            WorldCreator worldCreator = new WorldCreator(args[0]);
            worldCreator.generator(new VoidChunkGenerator());
            worldCreator.generateStructures(false);

            world = worldCreator.createWorld();

            if (world == null) {
                player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.fail")));
                return;
            }
        }

        player.teleport(new Location(world, 0, 65, 0, 0, 0));
    }

    @Subcommand("add")
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
        KBFFA.saveWorlds();
    }

    @Subcommand("remove")
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

        File file = new File(Bukkit.getServer().getWorldContainer(), args[0]);

        if (!file.exists()) {
            player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.no")));
            return;
        }

        World world = Bukkit.getWorld(args[0]);

        if (world == null) {
            WorldCreator worldCreator = new WorldCreator(args[0]);
            worldCreator.generator(new VoidChunkGenerator());
            worldCreator.generateStructures(false);

            world = worldCreator.createWorld();

            if (world == null) {
                player.sendMessage(MessageUtil.format(KBFFA.getMessages().getString("world.fail")));
                return;
            }
        }

        worlds.add(args[0]);
        KBFFA.getWorlds().set("worlds", worlds);
        KBFFA.saveWorlds();
    }

}
