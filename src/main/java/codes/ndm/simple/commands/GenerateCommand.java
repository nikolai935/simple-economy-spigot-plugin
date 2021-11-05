package codes.ndm.simple.commands;

import codes.ndm.simple.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenerateCommand implements CommandExecutor {
    private Economy plugin;
    public GenerateCommand(Economy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
            Player player;
            if(sender instanceof Player) {
                player = (Player) sender;
            } else {
                player = Bukkit.getPlayer(args[0]);
            }

            plugin.generateMoney(player.getUniqueId(), Integer.parseInt(args[1]));
            sender.sendMessage(ChatColor.DARK_GREEN + "Money Generated!");
        }
        return true;
    }
}
