package dev.hyperlisk.chatmaster.events;


import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ChannelMessageEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID origin;
    private final String message;
    private final String channel;

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


    public ChannelMessageEvent(UUID origin, String message, String channel) {
        this.origin = origin;
        this.message = message;
        this.channel = channel;
        this.isCancelled = false;
    }


    public UUID getOrigin() {
        return origin;
    }

    public String getMessage() {
        return message;
    }

    public String getChannel() {
        return channel;
    }



}
