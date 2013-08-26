package me.PimpDuck.DCNear;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private CommandClass commandclass;

    @Override
    public void onEnable() {
        commandclass = new CommandClass();
        getCommand("near").setExecutor(commandclass);
    }

    @Override
    public void onDisable() {
    }
}
