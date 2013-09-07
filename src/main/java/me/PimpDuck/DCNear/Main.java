package me.PimpDuck.DCNear;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin{

	  private CommandClass CommandClass = new CommandClass(this);

	Logger log = Logger.getLogger("Minecraft");

	@Override
	public void onEnable(){
		log.info("[DCNear] has been enabled!");
        log.info("Copyright 2013 PimpDuck All rights reserved.");
		saveDefaultConfig();
        getCommand("near").setExecutor(this.CommandClass);

	}
	@Override
	public void onDisable(){
		log.info("[DCNear] has been disabled!");	
	}
}
