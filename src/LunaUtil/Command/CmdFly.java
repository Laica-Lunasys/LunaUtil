package LunaUtil.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.Util;

public class CmdFly implements CommandExecutor {
	
	LunaUtil plugin;
	
	public CmdFly(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender par1Sender, Command arg1, String arg2, String[] par4args) {
		Player player = null;
		if(!(par1Sender instanceof Player) && par4args.length < 1) {
			this.plugin.getLogger().info("Can\'t execute this command on console.");
			return false;
		} else {
			if(!Util.hasPerm(plugin.PermFLY, (par1Sender), plugin)){
				return false;
			}
		}
		
		if(par4args.length >= 1) {
			if((player = Util.findPlayer(par4args[0], plugin)) == null) {
				return false;
			}
		} else {
			player = (Player)par1Sender;
		}
		if(!player.getAllowFlight()){
			player.setAllowFlight(true);
		}
		player.setFlying(!player.isFlying());
		String var3 = Util.maskedStringReplace(
				this.plugin.getMessage("ChangeFly"),
				new String[][]{
					{"%player", player.getDisplayName()},
					{"%s", player.isFlying()?"On":"Off"}
				});
		this.plugin.getServer().broadcastMessage(plugin.getPrefix() + var3);
		return true;
	}
}
