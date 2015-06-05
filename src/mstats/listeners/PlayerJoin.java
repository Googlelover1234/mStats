package mstats.listeners;

import mstats.managers.ConfigManager;
import mstats.managers.Manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (ConfigManager.getInstance().getConfig().get("users." + p.getName()) == null) {
			ConfigManager.getInstance().getConfig()
					.set("users." + p.getName() + ".kills", 0);
			ConfigManager.getInstance().getConfig()
					.set("users." + p.getName() + ".deaths", 0);
			ConfigManager.getInstance().saveConfig();
			ConfigManager.getInstance().reloadConfig();
		}
		Manager.getInstance().scoreboardRefresh(p);
	}

}
