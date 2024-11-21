package dev.hyperlisk.chatmaster.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;

public class ListChannelCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public ListChannelCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2,
            @NotNull String[] arg3) {
        // TODO Auto-generated method stub
        return false;
    }
}
