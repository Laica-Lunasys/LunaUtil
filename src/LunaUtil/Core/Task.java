package LunaUtil.Core;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import LunaUtil.Util.WorldUtil;

import com.bergerkiller.bukkit.common.AsyncTask;
import com.bergerkiller.bukkit.common.utils.CommonUtil;

public class Task extends Thread {
	public static void delete(final CommandSender sender, final String worldname) {
		new AsyncTask("World deletion thread") {
			public void run() {
				if (new WorldUtil(LunaUtil.plugin).delWorld(worldname)) {
					CommonUtil.sendMessage(sender, ChatColor.GREEN + "World '" + worldname + "' has been removed!");
				} else {
					CommonUtil.sendMessage(sender, ChatColor.RED + "Failed to (completely) remove the world!");
				}
			}
		}.start();
	}
}
