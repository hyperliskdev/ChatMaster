package dev.hyperlisk.chatmaster.db;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;

import com.mongodb.client.model.Filters;

import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import dev.hyperlisk.chatmaster.ChatMaster;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHandler {

    ChatMaster plugin;

    private MongoClient mongoClient;
    private MongoDatabase database;

    public DatabaseHandler(ChatMaster plugin, String connectionString) {
        this.plugin = plugin;

        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("chatmaster");
    }

    public MongoCollection getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public void close() {
        mongoClient.close();
    }

    // Create a new document for a player.
    public boolean createDocument(UUID playerUUID, String username) {

        BasicDBObject whisperObject = new BasicDBObject();  // Whisper object
        BasicDBObject channelObject = new BasicDBObject();  // Channel object
        // If the player is currently whispering to someone
        whisperObject.put("enabled", false);
        // UUID of the player the user is whispering to
        whisperObject.put("target_uuid", "");
        // Time-Stamp
        whisperObject.put("last_whisper", "");

        ArrayList<String> channels = new ArrayList<>();
        channels.add("global");

        String listening = "global";

        channelObject.put("channels", channels);
        channelObject.put("listening", listening);

        // List of channels the player is listening to

        Document doc = new Document("uuid", playerUUID.toString())
                .append("username", username)
                .append("channels", channelObject)
                .append("whisper", whisperObject);

        InsertOneResult groups = getCollection("players").insertOne(doc);

        return groups.wasAcknowledged();
    }

    public boolean documentExists(UUID playerUUID) {
        // If the player document exists, return true
        // Otherwise, return false
        return getCollection("players").find(Filters.eq("uuid", playerUUID.toString())).first() != null;
    }

    public Document getPlayerDocument(UUID playerUUID) {

        Bson projections = Projections.fields(
                Projections.include("uuid", "username", "channels", "whisper"),
                Projections.excludeId());

        return (Document) getCollection("players")
                .find(Filters.eq(
                        "uuid", playerUUID.toString()))
                .projection(projections)
                .first();
    }

    public void updateWhisper(UUID playerUUID, boolean enabled, String targetUUID, String lastWhisper) {

        Bson filter = Filters.eq("uuid", playerUUID.toString());

        Bson update = new Document("whisper.enabled", enabled)
                .append("whisper.target_uuid", targetUUID)
                .append("whisper.last_whisper", lastWhisper);

        getCollection("players").updateOne(filter, new Document("", update));
    }

    public void updateChannel(UUID playerUUID, ArrayList<String> channels, String listening) {

        Bson filter = Filters.eq("uuid", playerUUID.toString());

        Bson update = new Document("channels.channels", channels)
                .append("channels.listening", listening);

        getCollection("players").updateOne(filter, new Document("", update));
    }

}
