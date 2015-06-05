package mstats.managers;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Manager {

	FileConfiguration config = ConfigManager.getInstance().getConfig();

	private Manager() {
	}

	static Manager instance = new Manager();

	public static Manager getInstance() {
		return instance;
	}

	public void setSpawn(Player p) {
		config.set("spawn.w", p.getWorld().getName());
		config.set("spawn.x", p.getLocation().getX());
		config.set("spawn.y", p.getLocation().getY());
		config.set("spawn.z", p.getLocation().getZ());
		config.set("spawn.pitch", p.getLocation().getPitch());
		config.set("spawn.yaw", p.getLocation().getYaw());
		ConfigManager.getInstance().reloadConfig();
	}

	public void teleportSpawn(Player p) {
		if (config.getString("spawn") == null) {
			p.sendMessage(ChatColor.RED + "Spawn isn't set.");
			return;
		}

		World w = Bukkit.getWorld(config.getString("spawn.w"));
		Double x = Double.valueOf(config.getDouble("spawn.x"));
		Double y = Double.valueOf(config.getDouble("spawn.y"));
		Double z = Double.valueOf(config.getDouble("spawn.z"));
		Float pitch = Float.valueOf((float) config.getDouble("spawn.pitch"));
		Float yaw = Float.valueOf((float) config.getDouble("spawn.yaw"));
		Location spawn = new Location(w, x.doubleValue(), y.doubleValue(),
				z.doubleValue());
		spawn.setPitch(pitch.floatValue());
		spawn.setYaw(yaw.floatValue());
		p.teleport(spawn);
	}

	@SuppressWarnings("deprecation")
	public void scoreboardRefresh(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("stats", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.DARK_RED + "Your Stats");
		Score kills = objective.getScore(Bukkit
				.getOfflinePlayer(ChatColor.YELLOW + "» " + ChatColor.GOLD
						+ "Kills"));
		kills.setScore(ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills"));
		Score deaths = objective.getScore(Bukkit
				.getOfflinePlayer(ChatColor.YELLOW + "» " + ChatColor.GOLD
						+ "Deaths"));
		deaths.setScore(ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths"));
		Score ks = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW
				+ "» " + ChatColor.GOLD + "Killstreak"));
		ks.setScore(ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".killstreak"));
		p.setScoreboard(board);
	}

	public void messageStats(Player p, CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6&l&m[ ----&8&l&m [&b&l" + p.getName()
						+ "'s Stats&8&l&m]&6&l&m ---- ]"));
		double kills = ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills");
		double deaths = ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths");
		double kd = kills / deaths;
		DecimalFormat dm = new DecimalFormat("#.##");
		String kdrounded = dm.format(kd);
		sender.sendMessage(ChatColor.AQUA + "Kills: " + kills);
		sender.sendMessage(ChatColor.AQUA + "Deaths: " + deaths);
		if (deaths <= 0.0) {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: " + kills);
		} else {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: "
					+ kdrounded);
		}
	}

	public void messageStats(OfflinePlayer p, CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6&l&m[ ----&8&l&m [&b&l" + p.getName()
						+ "'s Stats&8&l&m]&6&l&m ---- ]"));
		double kills = ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills");
		double deaths = ConfigManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths");
		double kd = kills / deaths;
		DecimalFormat dm = new DecimalFormat("#.##");
		String kdrounded = dm.format(kd);
		sender.sendMessage(ChatColor.AQUA + "Kills: " + kills);
		sender.sendMessage(ChatColor.AQUA + "Deaths: " + deaths);
		if (deaths <= 0.0) {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: " + kills);
		} else {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: "
					+ kdrounded);
		}
	}

	public void resetStats(Player p) {
		ConfigManager.getInstance().getConfig()
				.set("users." + p.getName() + ".kills", 0);
		ConfigManager.getInstance().getConfig()
				.set("users." + p.getName() + ".deaths", 0);
		ConfigManager.getInstance().getConfig()
				.set("users." + p.getName() + ".killstreak", 0);
		ConfigManager.getInstance().saveConfig();
		ConfigManager.getInstance().reloadConfig();
	}

}
