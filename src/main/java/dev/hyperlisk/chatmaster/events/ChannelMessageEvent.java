package dev.hyperlisk.chatmaster.events;


import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ChannelMessageEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID sender;
    private final String message;
    private final String channelName;


    private boolean isCancelled;

    public static HandlerList getHandlersList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }


    public ChannelMessageEvent(UUID sender, String message, String channelName) {
        this.sender = sender;
        this.message = message;
        this.channelName = channelName;
        this.isCancelled = false;
    }


    public UUID getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getChannelName() {
        return channelName;
    }



}
