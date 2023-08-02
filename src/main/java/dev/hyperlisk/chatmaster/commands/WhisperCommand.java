package dev.hyperlisk.chatmaster.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperCommand implements CommandExecutor {



    // /whisper <player> <message>
    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("You must specify a player to whisper to!");
            return true;
        }

        String targetName = args[0];
        String message = args[1];

        Player origin = (Player) sender;
        Player target = origin.getServer().getPlayer(targetName);

        // Set the group of the origin to the target, and vice versa

        // Set whispered to true for both players

        // Send the message to the target player

        return true;
    }
}
