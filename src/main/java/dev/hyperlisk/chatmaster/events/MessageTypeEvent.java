package dev.hyperlisk.chatmaster.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MessageTypeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private String message;

    private final ChatEventType type;
    public MessageTypeEvent(Player player, ChatEventType type, String message) {
        this.player = player;
        this.type = type;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public ChatEventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public enum ChatEventType {
        WHISPER,
        GROUP,
        DEFAULT
    }
}
