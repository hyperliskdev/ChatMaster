package dev.hyperlisk.chatmaster.commands;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JoinChannelCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public JoinChannelCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    // /joinchannel <channel>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
