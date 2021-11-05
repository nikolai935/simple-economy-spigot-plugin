package codes.ndm.simple.events;

import codes.ndm.simple.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private Economy plugin;
    public PlayerJoin(Economy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPlayedBefore()) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome Back!");
        } else {
            plugin.insertNewPlayer(player.getUniqueId(), player.getName());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome!");
        }
    }
}
