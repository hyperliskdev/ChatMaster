package dev.hyperlisk.chatmaster.commands;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public GroupCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    // /group <group_name>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("You must specify a group name!");
            return true;
        }

        String groupName = args[0];
        Player player = (Player) sender;

        // Set the group of the player to the specified group
        dbHandler.setGroup(player.getUniqueId(), groupName);

        return true;
    }
}
