package mstats.main;

import mstats.cmds.Reset;
import mstats.cmds.Stats;
import mstats.listeners.PlayerDeath;
import mstats.listeners.PlayerJoin;
import mstats.managers.ConfigManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Economy econ = null;

	public void onEnable() {
		if (!setupEconomy()) {
			getLogger().severe(
					String.format("[%s] Disabled - No vault dependancy found.",
							this.getDescription().getName()));
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		ConfigManager.getInstance().setup(this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new PlayerJoin(), this);
		Bukkit.getServer().getPluginManager()
				.registerEvents(new PlayerDeath(), this);
		getCommand("Stats").setExecutor(new Stats());
		getCommand("Reset").setExecutor(new Reset());
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
