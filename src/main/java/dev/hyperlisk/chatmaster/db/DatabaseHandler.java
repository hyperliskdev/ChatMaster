package dev.hyperlisk.chatmaster.db;

import com.mongodb.client.*;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.connection.QueryResult;
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

    public boolean createPlayerDoc(UUID uuid, String name, String target, boolean whispered) {

        Document doc = new Document("uuid", uuid.toString()).append("name", name).append("target", target).append("whisper", whispered);

        InsertOneResult groups = getCollection("groups").insertOne(doc);

        return groups.wasAcknowledged();
    }

    public Document getPlayerDoc(UUID player) {
        return (Document) getCollection("groups").find(Filters.eq("uuid", player.toString())).first();
    }


    // Set the target of the player to the specified group and ensure whisper is set to false
    public boolean setGroup(UUID player, String group) {
        Document query = (Document) getCollection("groups").find(Filters.eq("uuid", player.toString())).first();

        Bson updates = Updates.combine(Updates.set("target", group), Updates.set("whisper", false));

        UpdateOptions options = new UpdateOptions().upsert(true);


        try {
            UpdateResult result = getCollection("groups").updateOne(query, updates, options);
            return result.wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    // Set the whisper value to true and change the target to the UUID of the target player uuid
    public boolean setWhisper(UUID origin, UUID target) {
        Document originDoc = (Document) getCollection("groups").find(Filters.eq("uuid", origin.toString())).first();

        Document targetDoc = (Document) getCollection("groups").find(Filters.eq("uuid", target.toString())).first();

        Bson originUpdate = Updates.combine(Updates.set("whisper", true), Updates.set("target", targetDoc.getString("uuid")));

        Bson targetUpdate = Updates.combine(Updates.set("whisper", true), Updates.set("target", originDoc.getString("uuid")));

        try {
            UpdateResult originResult = getCollection("groups").updateOne(originDoc, originUpdate);
            UpdateResult targetResult = getCollection("groups").updateOne(targetDoc, targetUpdate);

            return originResult.wasAcknowledged() && targetResult.wasAcknowledged();

        } catch (Exception e) {
            e.printStackTrace();

            return false;

        }

    }

    // Get the whispered player for a specific player
    // Returns the UUID of the whispered player
    public String getWhispered(UUID player) {
        Document doc = (Document) getCollection("groups").find(Filters.eq("uuid", player.toString())).filter(Filters.eq("whisper", true)).first();

        try {
            return doc.getString("target");
        } catch (Exception e) {
            return null;
        }
    }


    // Get a list of players with a specific group.
    public ArrayList<String> getPlayersInGroup(String group) {
        ArrayList<String> players = new ArrayList<String>();

        // .find()
        MongoCursor<Document> cursor = getCollection("groups").find(Filters.eq("target", group)).filter(Filters.eq("whispered", false)).iterator();

        try {
            while (cursor.hasNext()) {
                players.add((cursor.next().getString("uuid")));
            }
        } finally {
            cursor.close();
        }

        return players;
    }

    public void close() {
        mongoClient.close();
    }

    public void toggleWhisper(UUID uniqueId) {
        Document doc = (Document) getCollection("groups").find(Filters.eq("uuid", uniqueId.toString())).first();

        boolean whisper = doc.getBoolean("whisper");

        Bson update = Updates.set("whisper", !whisper);

        getCollection("groups").updateOne(doc, update);
    }
}
