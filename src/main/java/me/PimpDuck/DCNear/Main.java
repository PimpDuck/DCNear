package me.PimpDuck.DCNear;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	CommandClass commandclass;
	
    @Override
    public void onEnable() {
    	log.info("[DCNear] has been enabled!");
        saveDefaultConfig();
        getCommand("near").setExecutor(commandclass);
    }

    @Override
    public void onDisable() {
    	log.info("[DCNear] has been disabled!");
    }
}
