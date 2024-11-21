package dev.hyperlisk.chatmaster.commands;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WhisperCommand implements CommandExecutor {

    private DatabaseHandler dbHandler;

    public WhisperCommand(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    // /whisper <player>
    // 1. To enable the whisper function, the player must type /whisper <player>
    // 2. To disable, the player must type /whisper
    // If the last whispered message was 20 minutes ago, disable the whisper function.
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player origin = (Player) sender;

        Document originDoc = dbHandler.getPlayerDocument(origin.getUniqueId());
        boolean enabled = originDoc.getBoolean("whisper.enabled");

        if (args.length == 0 && enabled) {
            originDoc.put("whisper.enabled", false);
            origin.sendMessage("Whispering disabled.");
            return true;
        }

        if (args.length == 1 && !enabled) {
            Player target = origin.getServer().getPlayer(args[0]);
            if (target == null) {
                origin.sendMessage("Player not found.");
                return true;
            }

            Document targetDoc = dbHandler.getPlayerDocument(target.getUniqueId());
            targetDoc.put("whisper.last", System.currentTimeMillis());
            targetDoc.put("whisper.enabled", true);
            originDoc.put("whisper.enabled", true);
            originDoc.put("whisper.target", target.getUniqueId().toString());
            origin.sendMessage("Whispering enabled with " + target.getName());

            return true;
        }

        return true;
    }
}
