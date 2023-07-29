package com.kragleh.kbffa;

import co.aikar.commands.PaperCommandManager;
import com.kragleh.kbffa.arena.ArenaManager;
import com.kragleh.kbffa.commands.WorldCMD;
import com.kragleh.kbffa.db.DataSource;
import com.kragleh.kbffa.util.VoidChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public final class KBFFA extends JavaPlugin {

    private static KBFFA plugin;
    private Logger log;
    public YamlConfiguration arenas;
    public static YamlConfiguration messages;
    public static File messagesFile;
    public static YamlConfiguration worlds;
    public static File worldsFile;

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

        // Load Managers
        ArenaManager.load(arenas);

        // Load Commands
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new WorldCMD());

        log.info("Plugin loaded in " + (System.currentTimeMillis() - now) + "ms");
    }

    @Override
    public void onDisable() {
        log.info("Saving configuration files!");
        try {
            messages.save(messagesFile);
            worlds.save(worldsFile);
            log.info("Configuration files saved!");
        } catch (IOException e) {
            log.severe("Unable to save a configuration file!");
        }
    }

    public void loadArenas() {
        File file = new File(getDataFolder(), "arenas.yml");

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

    public static YamlConfiguration getMessages() {
        return messages;
    }

    public static File getMessagesFile() {
        return messagesFile;
    }

    public static YamlConfiguration getWorlds() {
        return worlds;
    }

    public static File getWorldsFile() {
        return worldsFile;
    }

    public static KBFFA getPlugin() {
        return plugin;
    }

}
