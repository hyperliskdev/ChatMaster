package dev.hyperlisk.chatmaster.tasks;

import dev.hyperlisk.chatmaster.db.DatabaseHandler;

// If the last message sent was a whisper, start a timer to reset the whisper
public class WhisperResetTask implements Runnable {

    private final DatabaseHandler dbHandler;

    public WhisperResetTask(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;

    }

    @Override
    public void run() {
        // Reset the whisper after the config specified time
        // Configured in the config.yml
        // whisper-reset-delay: 5000
        
        




    }
}
