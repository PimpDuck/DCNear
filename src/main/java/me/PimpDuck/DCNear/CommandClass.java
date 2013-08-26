package me.PimpDuck.DCNear;

import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandClass extends JavaPlugin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
            double range = 0;
            for (PermissionAttachmentInfo pai : perms) {
                if (pai.getPermission().toLowerCase().contains("dcnear.distance")) {
                    int len = pai.getPermission().length();
                    range = Double.parseDouble(pai.getPermission().substring(16, len));
                    if (pai.getValue() == false) {
                        player.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "You don't have permission to do this!");
                        return false;
                    }
                    break;
                }
            }
            if (args.length > 0) {
                player.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Too many arguments!");
                return false;
            }
            if ((args.length == 0 && range > 0) || player.hasPermission("DCNear.*")) {
                if (player.hasPermission("DCNear.*" || player.isOp()) {
                    range = 100;
                }
                Location start_loc = player.getLocation();
                StringBuilder sb = new StringBuilder();
                for (Entity nearby : player.getNearbyEntities(range, range, range)) {
                    if (nearby instanceof Player) {
                        Location end_loc = nearby.getLocation();
                        int distance = (int) start_loc.distance(end_loc);
                        sb.append(((Player) nearby).getName()).append(" (").append(ChatColor.DARK_RED).append(distance).append("m").append(ChatColor.WHITE).append("), ");
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
            sender.sendMessage(ChatColor.DARK_BLUE + "[DCNear] " + ChatColor.DARK_RED + "Only in game players can use this command!");
            return false;
        }
        return false;
    }
}
