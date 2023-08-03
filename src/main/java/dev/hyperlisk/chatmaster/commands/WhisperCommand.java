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

        Player origin = (Player) sender;
        Player target = origin.getServer().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("That player is not online!");
            return true;
        }

        Document playerDoc = dbHandler.getPlayerDoc(origin.getUniqueId());

        // If the player is already whispereing to someone, stop whispering
        if (playerDoc.getBoolean("whispered")) {
            dbHandler.toggleWhisper(origin.getUniqueId());
            // Only change the origin because maybe the target wants to say something back.
            return true;
        }

        return true;
    }
}
