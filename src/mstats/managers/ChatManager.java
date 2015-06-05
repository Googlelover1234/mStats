package mstats.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatManager {

	private ChatManager() {
	}

	static ChatManager instance = new ChatManager();

	public static ChatManager getInstance() {
		return instance;
	}

	public void messageNoPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "I'm sorry, but you do not have permission to perform this command."
				+ " Please contact the server administrators if you believe that this is in error.");

	}
	
	public void messageSenderPlayerOnly(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
		
	}

}
