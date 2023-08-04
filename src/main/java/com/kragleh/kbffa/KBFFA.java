package com.kragleh.kbffa;

import co.aikar.commands.PaperCommandManager;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.commands.ArenaCMD;
import com.kragleh.kbffa.commands.PearlCMD;
import com.kragleh.kbffa.commands.WorldCMD;
import com.kragleh.kbffa.db.DataSource;
import com.kragleh.kbffa.db.PlayerStorage;
import com.kragleh.kbffa.listeners.*;
import com.kragleh.kbffa.util.PAPIExpansion;
import com.kragleh.kbffa.util.VoidChunkGenerator;
import com.kragleh.kbffa.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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
    private static YamlConfiguration messages;
    private static YamlConfiguration arenas;
    private static File arenasFile;
    private static YamlConfiguration worlds;
    private static File worldsFile;
    private static YamlConfiguration kits;
    private static File kitsFile;

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
            con.close();
            log.info("Mysql connection success!");
        } catch (SQLException e) {
            log.severe("Unable to connect to mysql!");
            this.getServer().shutdown();
        }

        // Load Database Tables
        PlayerStorage.init();

        // Load Configuration Files
        loadWorlds();
        loadArenas();
        loadMessages();
        loadKits();

        // Load Worlds

        List<String> worldList = worlds.getStringList("worlds");

        for (String s : worldList) {
            WorldUtil.loadWorld(s);
        }

        // Load Commands
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new WorldCMD());
        manager.registerCommand(new ArenaCMD());
        manager.registerCommand(new PearlCMD());

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
            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(ArenaManager.getCurrent().getSpawn()));
        }, 0, getConfig().getInt("arena.switcher") * 1000L);

        // Register Listeners
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new HandSwitchListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);

        // Register Placeholders
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIExpansion(this).register();
        }

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

        if (!file.exists()) {
            saveResource("messages.yml", true);
        }

        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getMessages() {
        return messages;
    }

    public void loadWorlds() {
        File file = new File(getDataFolder(), "worlds.yml");
        worldsFile = file;

        if (!file.exists()) {
            saveResource("worlds.yml", true);
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

        if (!file.exists()) {
            saveResource("arenas.yml", true);
        }

        arenas = YamlConfiguration.loadConfiguration(file);
    }
    public static YamlConfiguration getArenas() {
        return arenas;
    }

    public void loadKits() {
        File file = new File(getDataFolder(), "kits.yml");
        kitsFile = file;

        if (!file.exists()) {
            saveResource("kits.yml", true);
        }

        kits = YamlConfiguration.loadConfiguration(file);
    }
    public static YamlConfiguration getKits() {
        return kits;
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
