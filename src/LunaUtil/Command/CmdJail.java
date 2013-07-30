package LunaUtil.Command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.Util;

public class CmdJail implements CommandExecutor {

	LunaUtil plugin;
	String SOStr,ERStr;

	public CmdJail(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
	}
	@SuppressWarnings({ })
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3CmdLine, String[] par4args) {
		Player player = null;
		if(!(par1Sender instanceof Player)) {
			this.plugin.getLogger().info("Can\'t execute this command on console.");
			return false;
		}
		if(par4args.length < 1){
			this.plugin.getLogger().info("Invalid Argument.");
			return false;
		}
		player = ((Player)par1Sender);
		if(par4args[0].equalsIgnoreCase("set")){
			if(!Util.hasPerm(plugin.PermJailSet, player, plugin)){
				return false;
			}
			plugin.jail.setLocation("Location", player.getLocation());
			plugin.jail.save();
			par1Sender.sendMessage(
					plugin.getPrefix() + Util.maskedStringReplace(
							plugin.getMessage("SetJail"),null));
		}else if (par4args[0].startsWith("add")){
			if(!Util.hasPerm(plugin.PermJailAdd, player, plugin)){
				return false;
			}
			List<String> var1 = plugin.jail.getStringList("Players");
			if(!var1.contains(par4args[1])){
				var1.add(par4args[1]);
			}
			plugin.jail.setStringList("Players", var1);
			System.out.println("findPlayer");
			Player var2 = null;
			if((var2 = Util.findPlayer(par4args[1], plugin, par1Sender)) != null){
				var2.teleport(plugin.jail.getLocation("Location"));
			}
			System.out.println("after findPlayer");
			plugin.getServer().broadcastMessage(
				plugin.getPrefix() + Util.maskedStringReplace(
						plugin.getMessage("tpJail"),null));
			plugin.jail.save();
		}else if (par4args[0].startsWith("remove")){
			if(!Util.hasPerm(plugin.PermJailRemove, player, plugin)){
				return false;
			}
			List<String> var1 = plugin.jail.getStringList("Players");
			var1.add(par4args[1]);
			while(var1.remove(par4args[1])){};
			plugin.jail.setStringList("Players", var1);
			
			plugin.jail.save();
			par1Sender.sendMessage(
					plugin.getPrefix() + Util.maskedStringReplace(
							plugin.getMessage("unJail"),null));
		}
		return true;
	}
}
