package dev.hyperlisk.chatmaster.commands;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinChannelCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public JoinChannelCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    // /group <group_name>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
