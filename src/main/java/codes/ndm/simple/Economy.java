package codes.ndm.simple;

import codes.ndm.simple.commands.BalanceCommand;
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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

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
        this.getCommand("balance").setExecutor(new BalanceCommand(this));

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

    public int getMoney(UUID uuid) {
        try {
            Document r = userCollection.find(eq("uuid", uuid.toString())).first();
            return r.getInteger("money");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void generateMoney(UUID uuid, int amount) {
        try {
            userCollection.findOneAndUpdate(eq("uuid", uuid.toString()), inc("money", amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
