package dev.hyperlisk.chatmaster;

import dev.hyperlisk.chatmaster.commands.GroupCommand;
import dev.hyperlisk.chatmaster.commands.WhisperCommand;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import dev.hyperlisk.chatmaster.listeners.MessageListener;
import dev.hyperlisk.chatmaster.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatMaster extends JavaPlugin {

    private DatabaseHandler dbHandler;

    @Override
    public void onEnable() {
        dbHandler = new DatabaseHandler(this, "mongodb://localhost:27017");

        getServer().getPluginManager().registerEvents(new MessageListener(this, dbHandler), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this, dbHandler), this);
        // Command Register
        getCommand("whisper").setExecutor(new WhisperCommand(dbHandler));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
