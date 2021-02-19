package fr.syrql.blade;

import fr.syrql.blade.command.BladeCommand;
import fr.syrql.blade.config.Config;
import fr.syrql.blade.config.ConfigManager;
import fr.syrql.blade.listener.BladeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainBlade extends JavaPlugin {

    private ConfigManager configManager;
    private Config configuration;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.registerManager();
        this.registerCommand();
        this.registerListener();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommand(){
        new BladeCommand(this);
    }

    private void registerListener(){
        this.getServer().getPluginManager().registerEvents(new BladeListener(this), this);
    }

    private void registerManager(){
        this.configManager = new ConfigManager(this);
        this.configuration = new Config(this, "config");
    }

    public Config getConfiguration() {
        return configuration;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
