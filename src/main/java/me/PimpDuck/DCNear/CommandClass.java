package me.PimpDuck.DCNear;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandClass extends JavaPlugin implements CommandExecutor {

    Main plugin;

    public CommandClass(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            double range = 0;
            if (player.hasPermission("dcnear.default") || player.hasPermission("dcnear.*")) {
                if (args.length > 1) {
                    player.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Too many arguments!");
                    return false;
                }
                if (args.length == 1 && player.hasPermission("dcnear.*")) {
                    try {
                        range = Double.parseDouble(args[0]);
                    } catch (NumberFormatException nfe) {
                        player.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Argument must be a number!");
                        return false;
                    }
                }
                if ((args.length == 0) && player.hasPermission("dcnear.god")) {
                    range = this.getConfig().getDouble("god");
                }else if((args.length == 0) && player.hasPermission("dcnear.ub3r")){
                	range = this.getConfig().getDouble("ub3r");
                }else if((args.length == 0) && player.hasPermission("dcnear.legend")){
                	range = this.getConfig().getDouble("legend");
                }else if((args.length == 0) && player.hasPermission("dcnear.super")){
                	range = this.getConfig().getDouble("super");
                }else if(!(player.hasPermission("dcnear.god") || player.hasPermission("dcnear.ub3r") || player.hasPermission("dcnear.legend") || player.hasPermission("dcnear.super"))){
                	player.sendMessage(ChatColor.DARK_RED + "You don't have permission to do this!");
                }
                if (range != 0) {
                    Location start_loc = player.getLocation();
                    StringBuilder sb = new StringBuilder();
                    for (Entity nearby : player.getNearbyEntities(range, range, range)) {
                        if (nearby instanceof Player) {
                            Location end_loc = nearby.getLocation();
                            int distance = (int) start_loc.distance(end_loc);
                            sb.append(((Player) nearby).getName()).append(" (").append(ChatColor.DARK_RED).append(distance).append("m").append(ChatColor.WHITE).append("), ");
                        }
                        String message;
                        if (sb.length() > 0) {
                            message = sb.toString().substring(0, (sb.length() - 2));
                        } else {
                            message = "none";
                        }
                        player.sendMessage(ChatColor.GOLD + "Players nearby: " + ChatColor.WHITE + message);
                    }
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "You do not have permission use this command!");
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Only in game players can use this command!");
            return false;
        }
        return false;
    }
}
