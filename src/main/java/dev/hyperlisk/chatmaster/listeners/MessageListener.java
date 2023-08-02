package dev.hyperlisk.chatmaster.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player origin = event.getPlayer();
        String message = event.getMessage();



    }


}
