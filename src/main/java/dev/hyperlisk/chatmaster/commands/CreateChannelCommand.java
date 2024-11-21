package dev.hyperlisk.chatmaster.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;

public class CreateChannelCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public CreateChannelCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    // /createchannel <channel>
    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2,  @NotNull String[] arg3) {
        return true;
    }
}
