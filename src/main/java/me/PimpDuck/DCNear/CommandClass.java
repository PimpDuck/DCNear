package me.PimpDuck.DCNear;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

	private Main plugin;

	public CommandClass(Main instance)
	{
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
    if(cmd.getName().equalsIgnoreCase("near")){
		Player player = (Player) sender;
		String prefix = plugin.getConfig().getString("DCNear.Chat-Prefix");
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		
			if ((sender instanceof Player)) {
		          if (args.length > 0)
		          {
		            if (args[0].equalsIgnoreCase("help")) {
		            	player.sendMessage(ChatColor.RED + "=============" + ChatColor.BLUE + "[ " + ChatColor.AQUA + "Diamcraft Near" + ChatColor.BLUE + " ]" + ChatColor.RED + "=============");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "(/near) shows who's near you.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "(/near help) shows you this page of course. :P");
		            	player.sendMessage(ChatColor.RED + "------------------" + ChatColor.GOLD + "Ranks" + ChatColor.RED + "------------------");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "GOD ranks distance is set to " + plugin.getConfig().getInt("DCNear.Distance.God") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "UB3R ranks distance is set to " + plugin.getConfig().getInt("DCNear.Distance.Ub3r") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "Legend ranks distance is set to " + plugin.getConfig().getInt("DCNear.Distance.Legend") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "Super ranks distance is set to " + plugin.getConfig().getInt("DCNear.Distance.Super") + " blocks.");
		            	player.sendMessage(ChatColor.RED + "=========================================");
						return false;
		            }
		            if(args[0].equalsIgnoreCase("reload")){
		            	if(player.hasPermission("dcnear.reload") || player.hasPermission("dcnear.*")){
		            	plugin.reloadConfig();
		                player.sendMessage(prefix + ChatColor.GOLD + "Configuration Reloaded!");
		                return false;
		            }else{
		            	player.sendMessage(prefix + ChatColor.RED + "You don't have permission to do this!");
		            	return true;
		            }
		           }
		          }
		          
		          if(args.length >= 1 && !args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("reload") && !player.hasPermission("dcnear.*")){
		        	  player.sendMessage(prefix + ChatColor.DARK_RED + "Too many arguments!");
		        	  return true;
		          }
		          
				double range = 0;
		          if (Main.playerDelayed(player))
					{
						if(Main.getRemainingTime(player) < 1)
						{
			                Main.removeDelayedPlayer(player);
						}else
						{
							int remaining = Main.getRemainingTime(player);
							String minutes = remaining == 1 ? " minute" : " minutes";

							player.sendMessage(prefix + ChatColor.GOLD + ("You must wait " + remaining + minutes + " before using /near again!"));
							return false;
						}
					}
				if (player.hasPermission("dcnear.god") || player.hasPermission("dcnear.ub3r") || player.hasPermission("dcnear.legend") || player.hasPermission("dcnear.super") || player.hasPermission("dcnear.*")) {
					if (args.length == 1 && player.hasPermission("dcnear.*")) {
						try {
							range = Double.parseDouble(args[0]);
						} catch (NumberFormatException nfe) {
							player.sendMessage(prefix + ChatColor.RED + "Argument must be a number!");
							return false;
						}
					}
					if (args.length == 0 && player.hasPermission("dcnear.god")) {
						range = plugin.getConfig().getDouble("DCNear.Distance.God");	
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.ub3r")){
						range = plugin.getConfig().getDouble("DCNear.Distance.Ub3r");
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.legend")){
						range = plugin.getConfig().getDouble("DCNear.Distance.Legend");
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.super")){
							range = plugin.getConfig().getDouble("DCNear.Distance.Super");
							Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.*")){
						player.sendMessage(prefix + ChatColor.GOLD + "Try /near [Number]");
						Main.removeDelayedPlayer(player);
						return true;
					}
					if (range != 0) {
						Location start_loc = player.getLocation();
						StringBuilder sb = new StringBuilder();
						for (Entity nearbyEntity : player.getNearbyEntities(range, range, range)) {
							if (nearbyEntity instanceof Player) {
								Location end_loc = nearbyEntity.getLocation();
								int distance = (int) start_loc.distance(end_loc);

								if(distance <= range){
									sb.append(((Player) nearbyEntity).getName()).append(" (").append(ChatColor.DARK_RED).append(distance).append("m").append(ChatColor.WHITE).append("), ");
								}
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
					player.sendMessage(prefix + ChatColor.RED + "You are not a high enough rank to do this! Do /near help for more information.");
					return true;
				}
			} else {
				player.sendMessage(prefix + ChatColor.DARK_RED + "Only in game players can use this command!");
				return false;
			}
			return true;
		}
		return false;
	}
}
