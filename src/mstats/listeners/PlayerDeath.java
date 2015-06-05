package mstats.listeners;

import mstats.managers.ConfigManager;
import mstats.managers.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

	int oldKS;

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity ent = e.getEntity();
		if (ent instanceof Player) {
			Player p = (Player) ent;
			this.oldKS = Integer
					.valueOf(ConfigManager.getInstance().getConfig()
							.getInt("users." + p.getName() + ".killstreak"));
			if (p.getKiller() instanceof Player) {
				ConfigManager
						.getInstance()
						.getConfig()
						.set("users." + p.getKiller().getName() + ".kills",
								Integer.valueOf(ConfigManager
										.getInstance()
										.getConfig()
										.getInt("users."
												+ p.getKiller().getName()
												+ ".kills") + 1));
				ConfigManager
						.getInstance()
						.getConfig()
						.set("users." + p.getKiller().getName() + ".killstreak",
								Integer.valueOf(ConfigManager
										.getInstance()
										.getConfig()
										.getInt("users."
												+ p.getKiller().getName()
												+ ".killstreak") + 1));
				ConfigManager.getInstance().saveConfig();
				ConfigManager.getInstance().reloadConfig();
				Manager.getInstance().scoreboardRefresh(p.getKiller());
				ConfigManager
						.getInstance()
						.getConfig()
						.set("users." + p.getName() + ".deaths",
								Integer.valueOf(ConfigManager
										.getInstance()
										.getConfig()
										.getInt("users." + p.getName()
												+ ".deaths") + 1));
				ConfigManager.getInstance().getConfig()
						.set("users." + p.getName() + ".killstreak", 0);
				ConfigManager.getInstance().saveConfig();
				ConfigManager.getInstance().reloadConfig();
				Manager.getInstance().scoreboardRefresh(p);

				int killerKS = Integer.valueOf(ConfigManager
						.getInstance()
						.getConfig()
						.getInt("users." + p.getKiller().getName()
								+ ".killstreak"));
				if (killerKS == 5 || killerKS == 10) {
					Bukkit.broadcastMessage(ChatColor
							.translateAlternateColorCodes('&',
									"&8&l[&b&mStats&8&l]&b&l Whoa! &6&l"
											+ p.getKiller().getName()
											+ " &b&lis on a &6&l" + killerKS
											+ " &b&lkillstreak!"));
				}

				if (oldKS == 5 || oldKS == 10) {
					Bukkit.broadcastMessage(ChatColor
							.translateAlternateColorCodes('&',
									"&8&l[&b&mStats&8&l]&b&l Watch out! &6&l"
											+ p.getName()
											+ " &b&llost their &6&l" + oldKS
											+ " &b&lkillstreak to &6&l "
											+ p.getKiller().getName() + "&b&l!"));
				}

				killerKS = 0;
				oldKS = 0;
			} else
				return;
		} else
			return;
	}
}
