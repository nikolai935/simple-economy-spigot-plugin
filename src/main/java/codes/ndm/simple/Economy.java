package codes.ndm.simple;

import codes.ndm.simple.events.PlayerJoin;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.UUID;

public final class Economy extends JavaPlugin {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> userCollection;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        connect();

        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin Enabled");
    }

    @Override
    public void onDisable() {
        mongoClient.close();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Plugin Disabled!");
    }

    public void connect() {
        ConnectionString connectionString = new ConnectionString(getConfig().getString("conn"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(getConfig().getString("database"));
        userCollection = database.getCollection("economy");
    }

    public void insertNewPlayer(UUID uuid, String name) {
        try {
            Document doc = new Document("uuid", uuid.toString()).append("name", name).append("money", 0).append("joined", new Date().toString());
            userCollection.insertOne(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
