package LunaUtil.Command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.Util;

public class CmdGamemode implements CommandExecutor {

	LunaUtil plugin;


	public CmdGamemode(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
	}

	@Override
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3CmdLine, String[] par4args) {
		Player player = null;
		if(!(par1Sender instanceof Player) && par4args.length < 1) {
			this.plugin.getLogger().info("Can\'t execute this command on console.");
			return false;
		} else {
			if(!Util.hasPerm(plugin.PermGM, (par1Sender), plugin)){
				return false;
			}
			
			Integer var1;
			try {
				var1 = Integer.valueOf(par4args[0]);
			} catch (Exception var10) {
				var1 = null;
			}

			int var2 = par4args.length == 2?1:0;
			if(par4args.length >= 1 && var1 == null) {
				if((player = Util.findPlayer(par4args[var2], plugin)) == null) {
					return false;
				}

				player = this.plugin.getServer().getPlayer(par4args[var2]);
			} else {
				player = (Player)par1Sender;
			}
			
			GameMode mode = player.getGameMode() == 
					GameMode.SURVIVAL?GameMode.CREATIVE:GameMode.SURVIVAL;
			if(var1 != null) {
				if(var1.intValue() > 3) {
					return false;
				}

				mode = GameMode.getByValue(var1.intValue());
			}

			player.setGameMode(mode);
			String var3 = Util.maskedStringReplace(
					this.plugin.getMessage("ChangeGM"),
					new String[][]{{"%player", player.getDisplayName()},
						{"%gamemode", mode.toString()}});
			this.plugin.getServer().broadcastMessage(plugin.getPrefix() + var3);
			return true;
		}
	}
}
