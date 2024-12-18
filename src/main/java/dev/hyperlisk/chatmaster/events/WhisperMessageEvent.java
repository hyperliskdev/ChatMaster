package dev.hyperlisk.chatmaster.events;


import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class WhisperMessageEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID origin;
    private final UUID reciever;
    private final String message;

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }


    public static HandlerList getHandlersList() {
        return HANDLERS;
    }

    public WhisperMessageEvent(UUID origin, UUID reciever, String message) {
        this.origin = origin;
        this.reciever = reciever;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public UUID getReciever() {
        return reciever;
    }

    public UUID getOrigin() {
        return origin;
    }
}
