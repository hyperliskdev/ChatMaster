package dev.hyperlisk.chatmaster;

import dev.hyperlisk.chatmaster.commands.GroupCommand;
import dev.hyperlisk.chatmaster.commands.WhisperCommand;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatMaster extends JavaPlugin {

    private DatabaseHandler dbHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        dbHandler = new DatabaseHandler(this, "mongodb://localhost:27017");

        // Command Register
        getCommand("whisper").setExecutor(new WhisperCommand(dbHandler));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
