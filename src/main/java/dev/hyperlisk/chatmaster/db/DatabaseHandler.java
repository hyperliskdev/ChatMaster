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

    public boolean createDocument(UUID playerUUID, String username) {

        BasicDBObject whisperObject = new BasicDBObject();

        // If the player is currently whispering to someone
        whisperObject.put("enabled", false);

        // UUID of the player the user is whispering to
        whisperObject.put("target_uuid", "");

        // Time-Stamp
        whisperObject.put("last_whisper", "");


        BasicDBObject channelObject = new BasicDBObject();

        // Current channel the player is in
        channelObject.put("current", "global");

        // List of channels the player is listening to
        ArrayList<String> listening = new ArrayList<>();
        listening.add("global");
        channelObject.put("listening", listening);

        Document doc = new Document("uuid", playerUUID.toString()).append("username", username).append("channels", channelObject).append("whisper", whisperObject);

        InsertOneResult groups = getCollection("players").insertOne(doc);

        return groups.wasAcknowledged();
    }

    public Document getPlayerDocument(UUID playerUUID) {

        Bson projections = Projections.fields(
                Projections.include("uuid", "username", "channels", "whisper"),
                Projections.excludeId()
        );

        return (Document) getCollection("players")
                .find(Filters.eq(
                        "uuid", playerUUID.toString()
                )).projection(projections)
                .first();
    }


    public void close() {
        mongoClient.close();
    }
}
