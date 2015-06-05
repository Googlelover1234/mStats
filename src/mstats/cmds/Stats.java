package mstats.cmds;

import mstats.managers.ConfigManager;
import mstats.managers.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length < 1) {
				Manager.getInstance().messageStats(p, p);
				return true;
			} else if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					if (ConfigManager.getInstance().getConfig()
							.get("users." + args[0]) == null) {
						p.sendMessage(ChatColor.RED
								+ "Sorry, that player doesn't seem to exist.");
						p.sendMessage(ChatColor.RED + "*NOTE* CaSe SeNsItIvE");
						return true;
					} else {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
						Manager.getInstance().messageStats(op, p);
						return true;
					}
				} else {
					Manager.getInstance().messageStats(target, p);
					return true;
				}
			} else
				return false;
		}

		return true;
	}

}
