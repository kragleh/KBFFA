package com.kragleh.kbffa;

import co.aikar.commands.PaperCommandManager;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.commands.ArenaCMD;
import com.kragleh.kbffa.commands.WorldCMD;
import com.kragleh.kbffa.db.DataSource;
import com.kragleh.kbffa.events.*;
import com.kragleh.kbffa.util.VoidChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public final class KBFFA extends JavaPlugin {

    private static KBFFA plugin;
    private Logger log;
    private static YamlConfiguration arenas;
    private static File arenasFile;
    private static YamlConfiguration messages;
    private static File messagesFile;
    private static YamlConfiguration worlds;
    private static File worldsFile;

    @Override
    public void onEnable() {
        long now = System.currentTimeMillis();
        plugin = this;
        log = getLogger();

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
            log.severe("This plugin isn't configured yet, please fill out the config.yml file and then start the server!");
            this.getServer().shutdown();
        }

        log.info("Testing mysql connection!");
        try (Connection con = DataSource.getConnection()) {
            log.info("Mysql connection success!");
        } catch (SQLException e) {
            log.severe("Unable to connect to mysql!");
            this.getServer().shutdown();
        }

        // Load Database Tables

        // Load Configuration Files
        loadWorlds();
        loadArenas();
        loadMessages();

        // Load Worlds

        List<String> worldList = worlds.getStringList("worlds");

        for (String s : worldList) {
            World world = Bukkit.getWorld(s);

            if (world == null) {
                WorldCreator worldCreator = new WorldCreator(s);
                worldCreator.generator(new VoidChunkGenerator());
                worldCreator.generateStructures(false);

                worldCreator.createWorld();
            }
        }

        // Load Commands
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new WorldCMD());
        manager.registerCommand(new ArenaCMD());

        if (getConfig().getBoolean("setup")) {
            log.warning("Currently in setup mode! Change the setup value in config.yml to enter game mode!");
            return;
        }

        // Load Managers
        ArenaManager.init(arenas);


        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            Random r = new Random();
            int nextArena = r.nextInt(ArenaManager.arenas.size());
            ArenaManager.setCurrent(ArenaManager.arenas.get(nextArena));
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.teleport(ArenaManager.getCurrent().getSpawn());
            });
        }, 0, getConfig().getInt("arena.switcher") * 1000L);


        // Register Events
        getServer().getPluginManager().registerEvents(new OnDamage(), this);
        getServer().getPluginManager().registerEvents(new OnDrop(), this);
        getServer().getPluginManager().registerEvents(new OnDeath(), this);
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnHandSwitch(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);

        log.info("Plugin loaded in " + (System.currentTimeMillis() - now) + "ms");
    }

    @Override
    public void onDisable() {
        log.info("Saving configuration files!");
        try {
            worlds.save(worldsFile);
            log.info("Worlds.yml saved!");
            arenas.save(arenasFile);
            log.info("Arenas.yml saved!");
            log.info("Files successfully saved!");
        } catch (IOException e) {
            log.severe("Unable to save a configuration file!");
        }
    }

    public void loadMessages() {
        File file = new File(getDataFolder(), "messages.yml");
        messagesFile = file;

        try {
            if (!file.exists()) {
                file.createNewFile();
                saveResource("messages.yml", true);
            }
        } catch (IOException e) {
            log.severe("Unable to create messages.yml file!");
            getPluginLoader().disablePlugin(this);
        }

        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getMessages() {
        return messages;
    }

    public static boolean saveMessages() {
        try {
            messages.save(messagesFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loadWorlds() {
        File file = new File(getDataFolder(), "worlds.yml");
        worldsFile = file;

        try {
            if (!file.exists()) {
                file.createNewFile();
                saveResource("worlds.yml", true);
            }
        } catch (IOException e) {
            log.severe("Unable to create worlds.yml file!");
            getPluginLoader().disablePlugin(this);
        }

        worlds = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getWorlds() {
        return worlds;
    }

    public static boolean saveWorlds() {
        try {
            worlds.save(worldsFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loadArenas() {
        File file = new File(getDataFolder(), "arenas.yml");
        arenasFile = file;

        try {
            if (!file.exists()) {
                file.createNewFile();
                saveResource("arenas.yml", true);
            }
        } catch (IOException e) {
            log.severe("Unable to create arenas.yml file!");
            getPluginLoader().disablePlugin(this);
        }

        arenas = YamlConfiguration.loadConfiguration(file);
    }
    public static YamlConfiguration getArenas() {
        return arenas;
    }

    public static boolean saveArenas() {
        try {
            arenas.save(arenasFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static KBFFA getPlugin() {
        return plugin;
    }

}
