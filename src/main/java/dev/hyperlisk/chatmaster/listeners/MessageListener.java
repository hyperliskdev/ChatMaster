package dev.hyperlisk.chatmaster.listeners;

import dev.hyperlisk.chatmaster.ChatMaster;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageListener implements Listener {

    private final ChatMaster plugin;
    private final DatabaseHandler dbHandler;

    public MessageListener(ChatMaster plugin, DatabaseHandler dbHandler) {
        this.plugin = plugin;
        this.dbHandler = dbHandler;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player origin = event.getPlayer();
        String message = event.getMessage();

        // Look in the database if the player has a valid online uuid for the whispered player
        Document playerDoc = dbHandler.getPlayerDoc(origin.getUniqueId());
        String target = playerDoc.getString("target");

        if (playerDoc.getBoolean("whispered")) {
            // Check if the player is online
            if (plugin.getServer().getPlayer(target) == null) {
                origin.sendMessage("§cThe player you are trying to whisper is not online.");
                return;
            }

            // Send the message to the whispered player
            Player targetPlayer = plugin.getServer().getPlayer(target);
            targetPlayer.sendMessage(message);
        } else {
            dbHandler.getPlayersInGroup(target).forEach(player -> {
                Player targetPlayer = plugin.getServer().getPlayer(player);
                targetPlayer.sendMessage(message);
            });
        }

    }


}
