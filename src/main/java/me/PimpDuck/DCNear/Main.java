package me.PimpDuck.DCNear;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("near").setExecutor(new CommandClass(this));
    }

    @Override
    public void onDisable() {
    }
}
