package dev.hyperlisk.chatmaster.listeners;

import dev.hyperlisk.chatmaster.ChatMaster;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import dev.hyperlisk.chatmaster.events.ChannelMessageEvent;
import dev.hyperlisk.chatmaster.events.WhisperMessageEvent;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.print.Doc;
import java.util.UUID;

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
        Document playerDoc = dbHandler.getPlayerDocument(origin.getUniqueId());

        if (playerDoc.get("whisper", Document.class).getBoolean("enabled")) {
            // Trigger a whisper message event
            UUID recieverUuid = playerDoc.get("whisper", Document.class).get("target_uuid", UUID.class);

            if(this.plugin.getServer().getPlayer(recieverUuid) == null) {
                origin.sendMessage("The player you are trying to whisper to is not online.");
                return;
            }

            WhisperMessageEvent whisperMessageEvent = new WhisperMessageEvent(origin.getUniqueId(), recieverUuid, message);
            plugin.getServer().getPluginManager().callEvent(whisperMessageEvent);


        } else {
            // Trigger a channel message event
            String currentChannel = playerDoc.get("channels", Document.class).getString("current");

            ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(origin.getUniqueId(), message, currentChannel);
            plugin.getServer().getPluginManager().callEvent(channelMessageEvent);

        }


    }


    @EventHandler
    public void onWhisperMessageEvent(WhisperMessageEvent event) {




    }

    @EventHandler
    public void onChannelMessageEvent(ChannelMessageEvent event) {



    }


}
