package me.PimpDuck.DCNear;



import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

	List<String> nearby = new ArrayList<String>();
	
	  private Main plugin;

	  public CommandClass(Main instance)
	  {
	    this.plugin = instance;
	  }
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
    	if(cmd.getName().equalsIgnoreCase("dcnear")){
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            double range = 0;
            if (player.hasPermission("dcnear.god") || player.hasPermission("dcnear.ub3r") || player.hasPermission("dcnear.legend") || player.hasPermission("dcnear.super") || player.hasPermission("dcnear.*")) {

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
                if (args.length == 0 && player.hasPermission("dcnear.god")) {
                    range = this.plugin.getConfig().getDouble("god");
                }
                if(args.length == 0 && player.hasPermission("dcnear.ub3r")){
                    range = this.plugin.getConfig().getDouble("ub3r");
                }
                if(args.length == 0 && player.hasPermission("dcnear.legend")){
                    range = this.plugin.getConfig().getDouble("legend");
                }
                if(args.length == 0 && player.hasPermission("dcnear.super")){
                	if(range < this.plugin.getConfig().getDouble("super")){
                    range = this.plugin.getConfig().getDouble("super");	
                	}
                }

                if (range != 0) {
                    Location start_loc = player.getLocation();
                    StringBuilder sb = new StringBuilder();
                    for (Entity nearbyEntity : player.getNearbyEntities(range, range, range)) {
                    	if (nearbyEntity instanceof Player) {
                    	nearby.add(((Player) nearbyEntity).getName());
                    	Location end_loc = nearbyEntity.getLocation();
                    	int distance = (int) start_loc.distance(end_loc);
                        sb.append(((Player) nearbyEntity).getName()).append(" (").append(ChatColor.DARK_RED).append(distance).append("m").append(ChatColor.WHITE).append("), ");
                    	}
                    	}
                    String message;
                    if (sb.length() > 0) {
                        message = sb.toString().substring(0, (sb.length() - 2));
                    } else {
                        message = "none";
                    }
                    player.sendMessage(ChatColor.GOLD + "Players nearby: " + ChatColor.WHITE + message);
                }
            } else {
                sender.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "You do not have permission use this command!");
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Only in game players can use this command!");
            return false;
        }
        return true;
        }
        return false;
    }
}
