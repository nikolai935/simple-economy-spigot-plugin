package codes.ndm.simple.commands;

import codes.ndm.simple.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private Economy plugin;
    public BalanceCommand(Economy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            if(args.length != 0) {
                Player player = Bukkit.getPlayer(args[0]);
                sender.sendMessage(ChatColor.GOLD + "This players balance is $" + plugin.getMoney(player.getUniqueId()));
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "You have to specify a player");
                return false;
            }
        }
        if(args.length != 0) {
            Player player = Bukkit.getPlayer(args[0]);
            sender.sendMessage(ChatColor.GOLD + "This players balance is $" + plugin.getMoney(player.getUniqueId()));
        } else {
            Player player = (Player) sender;
            sender.sendMessage(ChatColor.GOLD + "Your balance is $" + plugin.getMoney(player.getUniqueId()));
        }
        return true;
    }
}
