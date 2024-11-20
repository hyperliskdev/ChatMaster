package dev.hyperlisk.chatmaster.listeners;

import dev.hyperlisk.chatmaster.ChatMaster;
import dev.hyperlisk.chatmaster.db.DatabaseHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final ChatMaster plugin;

    private final DatabaseHandler dbHandler;

    public PlayerListener(ChatMaster plugin, DatabaseHandler dbHandler) {
        this.plugin = plugin;
        this.dbHandler = dbHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Create an empty document for the new player in the database
        if(!player.hasPlayedBefore()) {
            dbHandler.createDocument(player.getUniqueId(), player.getName());
        }


        


    }


}
