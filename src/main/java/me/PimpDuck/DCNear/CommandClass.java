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
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(cmd.getName().equalsIgnoreCase("near")){
			if ((sender instanceof Player)) {
				Player player = (Player) sender;
				double range = 0;
				
		          if (args.length > 0)
		          {
		            if (args[0].equalsIgnoreCase("info")) {
		            	player.sendMessage(ChatColor.RED + "===========" + ChatColor.BLUE + "[ " + ChatColor.AQUA + "Diamcraft Near" + ChatColor.BLUE + " ]" + ChatColor.RED + "===========");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "(/near) shows who's near you.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "(/near info) shows you this page of course. :P");
		            	player.sendMessage(ChatColor.RED + "------------------------" + ChatColor.GOLD + "Ranks" + ChatColor.RED + "------------------------");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "GOD ranks distance is set to " + this.plugin.getConfig().getInt("god") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "UB3R ranks distance is set to " + this.plugin.getConfig().getInt("ub3r") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "Legend ranks distance is set to " + this.plugin.getConfig().getInt("legend") + " blocks.");
		            	player.sendMessage(ChatColor.BLACK + "* " + ChatColor.GOLD + "Super ranks distance is set to " + this.plugin.getConfig().getInt("super") + " blocks.");
		            	player.sendMessage(ChatColor.RED + "==================================");
		            }
		          }
		          
		          if (Main.playerDelayed(player))
					{ //The player is delayed
						if(Main.getRemainingTime(player) < 1)
						{ //The delay is over
			                Main.removeDelayedPlayer(player);
						}else
						{ //The delay isn't over
							int remaining = Main.getRemainingTime(player);
							String minutes = remaining == 1 ? " minute" : " minutes";
							player.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "DC" + ChatColor.RED + "Near" + ChatColor.WHITE + "] " + ChatColor.GOLD + ("You must wait " + remaining + minutes + " before using /near again!"));
							return false;
						}
					}
				
				if (player.hasPermission("dcnear.god") || player.hasPermission("dcnear.ub3r") || player.hasPermission("dcnear.legend") || player.hasPermission("dcnear.super") || player.hasPermission("dcnear.*")) {
					if(args.length > 0 && !args[0].equalsIgnoreCase("info")){
						player.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "DC" + ChatColor.RED + "Near" + ChatColor.WHITE + "] " + ChatColor.RED + "Too many arguments!");
					}
					
					if (args.length == 1 && player.hasPermission("dcnear.*")) {
						try {
							range = Double.parseDouble(args[0]);
						} catch (NumberFormatException nfe) {
							player.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "DC" + ChatColor.RED + "Near" + ChatColor.WHITE + "] " + ChatColor.RED + "Argument must be a number!");
							return false;
						}
					}
					if (args.length == 0 && player.hasPermission("dcnear.god")) {
						range = this.plugin.getConfig().getDouble("god");	
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.ub3r")){
						range = this.plugin.getConfig().getDouble("ub3r");
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.legend")){
						range = this.plugin.getConfig().getDouble("legend");
						Main.addDelayedPlayer(player);
					}
					if(args.length == 0 && player.hasPermission("dcnear.super")){
							range = this.plugin.getConfig().getDouble("super");
							Main.addDelayedPlayer(player);
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
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "DC" + ChatColor.RED + "Near" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have permission use this command!");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "DC" + ChatColor.RED + "Near" + ChatColor.WHITE + "] " + ChatColor.DARK_RED + "Only in game players can use this command!");
				return false;
			}
			return true;
		}
		return false;
	}
}
