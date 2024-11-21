package dev.hyperlisk.chatmaster;

import dev.hyperlisk.chatmaster.commands.WhisperCommand;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import dev.hyperlisk.chatmaster.listeners.MessageListener;
import dev.hyperlisk.chatmaster.listeners.PlayerListener;
import dev.hyperlisk.chatmaster.tasks.WhisperResetTask;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatMaster extends JavaPlugin {

    private DatabaseHandler dbHandler;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        dbHandler = new DatabaseHandler(this, getConfig().getString("mongo-connection-string"));

        getServer().getPluginManager().registerEvents(new MessageListener(this, dbHandler), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this, dbHandler), this);

        // Command Register
        getCommand("whisper").setExecutor(new WhisperCommand(dbHandler));

        // Setup the whisper reset task
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new WhisperResetTask(dbHandler), 0L,
                getConfig().getLong("whisper-reset-delay"));


        getLogger().info("ChatMaster has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


        getLogger().info("ChatMaster has been disabled!");
    }
}
