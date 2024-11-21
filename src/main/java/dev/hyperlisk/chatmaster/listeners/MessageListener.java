package dev.hyperlisk.chatmaster.listeners;

import dev.hyperlisk.chatmaster.ChatMaster;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import dev.hyperlisk.chatmaster.events.ChannelMessageEvent;
import dev.hyperlisk.chatmaster.events.WhisperMessageEvent;

import io.papermc.paper.event.player.AsyncChatEvent;

import org.bson.Document;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MessageListener implements Listener {

    private final ChatMaster plugin;
    private final DatabaseHandler dbHandler;

    public MessageListener(ChatMaster plugin, DatabaseHandler dbHandler) {
        this.plugin = plugin;
        this.dbHandler = dbHandler;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player origin = event.getPlayer();
        String message = event.message().toString();

        // Look in the database if the player has a valid online uuid for the whispered
        // player
        Document playerDoc = dbHandler.getPlayerDocument(origin.getUniqueId());

        if (playerDoc.getBoolean("whisper.enabled")) {
            // Trigger a whisper message event
            UUID recieverUuid = playerDoc.get("whisper", Document.class).get("target_uuid", UUID.class);

            if (this.plugin.getServer().getPlayer(recieverUuid) == null) {
                origin.sendMessage("The player you are trying to whisper to is not online.");
                return;
            }

            WhisperMessageEvent whisperMessageEvent = new WhisperMessageEvent(origin.getUniqueId(), recieverUuid,
                    message);
            plugin.getServer().getPluginManager().callEvent(whisperMessageEvent);

        } else {
            // Trigger a channel message event
            String channel = playerDoc.getString("channel.active");

            ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(origin.getUniqueId(), channel, message);
            plugin.getServer().getPluginManager().callEvent(channelMessageEvent);

        }
    }

    @EventHandler
    public void onWhisperMessageEvent(WhisperMessageEvent event) {
        Player origin = this.plugin.getServer().getPlayer(event.getOrigin());
        Player reciever = this.plugin.getServer().getPlayer(event.getReciever());
        String message = event.getMessage();

        reciever.sendMessage("§7[§dWhisper§7] §f" + origin.getName() + " §8» §f" + message);
        origin.sendMessage("§7[§dWhisper§7] §f" + reciever.getName() + " §8» §f" + message);
    }

    @EventHandler
    public void onChannelMessageEvent(ChannelMessageEvent event) {
        Player origin = this.plugin.getServer().getPlayer(event.getOrigin());
        String channel = event.getChannel();
        String message = event.getMessage();

        if (!origin.hasPermission("chatmaster.channel." + channel)) {
            origin.sendMessage("§c You do not have permission to send messages in this channel.");
            return;
        }

        // Send message to each player with the cooresponding channeling in their
        // 'listening' list.
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            Document playerDoc = dbHandler.getPlayerDocument(player.getUniqueId());
            if (playerDoc.getList("channel.listening", String.class).contains(channel)) {
                player.sendMessage("§7[§d" + channel + "§7] §f" + origin.getName() + " §8» §f" + message);
            }
        }
    }

}
