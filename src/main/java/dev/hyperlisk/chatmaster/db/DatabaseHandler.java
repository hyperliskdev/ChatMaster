package dev.hyperlisk.chatmaster.db;

import com.mongodb.client.*;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.connection.QueryResult;
import dev.hyperlisk.chatmaster.ChatMaster;
import org.bson.Document;

import java.util.ArrayList;

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

    public boolean createPlayerDoc(String uuid, String name, String group, boolean whispered) {

        Document doc = new Document("uuid", uuid)
                .append("name", name)
                .append("group", group)
                .append("whispered", whispered);

        InsertOneResult groups = getCollection("groups").insertOne(doc);

        return groups.wasAcknowledged();
    }

    // Get a list of all groups
    public ArrayList<String> getGroups() {

        DistinctIterable distinct = getCollection("groups")
                .distinct("group",
                        Filters.eq("whispered", "false"),
                        String.class);

        ArrayList<String> listOfGroups = new ArrayList<String>();


        // TODO: Theres a better way like using the iterable for each or stream collection
        for (Object group : distinct) {
            listOfGroups.add((String) group);
        }

        return listOfGroups;
    }

    public boolean setGroup(String uuid, String group) {
        UpdateResult groupResult = getCollection("groups")
                .updateOne(Filters.eq("uuid", uuid),
                        new Document("$set", new Document("group", group)));

        return groupResult.wasAcknowledged();
    }


    // Get a list of players with a specific group.
    public ArrayList<String> getPlayersWithGroup(String group) {
        ArrayList<String> players = new ArrayList<String>();

        // .find()
        MongoCursor<Document> cursor = getCollection("groups").find(Filters.eq("group", group)).iterator();
        try {
            while (cursor.hasNext()) {
                players.add(cursor.next().getString("name"));
            }
        } finally {
            cursor.close();
        }

        return players;
    }

    public void close() {
        mongoClient.close();
    }

}
