package LunaUtil.Command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.Util;

public class CmdTransPort implements CommandExecutor {

	LunaUtil plugin;


	public CmdTransPort(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
	}
	@Override
	@SuppressWarnings({ })
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3CmdLine, String[] par4args) {
		if(!(par1Sender instanceof Player) && par4args.length < 1) {
			this.plugin.getLogger().info("Can\'t execute this command on console.");
			return false;
		} else {
			if(!Util.hasPerm(plugin.PermTP, (par1Sender), plugin)){
				return false;
			}
			Location loc = ((Player)par1Sender).getLocation();
			Player from = null, to = null;
			if(par2Command.getName().equalsIgnoreCase("tp")){ // /tp toPlayer
				from = (Player)par1Sender;
				if((to = Util.findPlayer(par4args[0], plugin)) == null){
					return false;
				}
				loc = to.getLocation();
			}else if(par2Command.getName().equalsIgnoreCase("tphere")){ // /tphere fromPlayer
				to = (Player)par1Sender;
				if((from = Util.findPlayer(par4args[0], plugin)) == null){
					return false;
				}
				loc = to.getLocation();
			}else if(par2Command.getName().equalsIgnoreCase("t2p")){ // /t2p fromPlayer toPlayer
				if((from = Util.findPlayer(par4args[0], plugin, par1Sender)) == null){
					return false;
				}
				if((to = Util.findPlayer(par4args[1], plugin, par1Sender)) == null){
					return false;
				}
				loc = to.getLocation();
			}else if(par2Command.getName().equalsIgnoreCase("tploc")){ // /tploc Player x y z 又は /tploc x y z
				final double x,y,z;
				if (par4args.length == 3){			//Playerが未指定の時
					x = Double.parseDouble(par4args[0]);
					y = Double.parseDouble(par4args[1]);
					z = Double.parseDouble(par4args[2]);
					from = (Player)par1Sender;
					loc = from.getLocation();
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					from = (Player)par1Sender;
				}else if(par4args.length == 4){  	//Player指定時
					if((from = Util.findPlayer(par4args[0], plugin, par1Sender)) == null){
						return false;
					}
					x = Double.parseDouble(par4args[1]);
					y = Double.parseDouble(par4args[2]);
					z = Double.parseDouble(par4args[3]);
					loc = from.getLocation();
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
				}else{
					return false;
				}
			}
			teleport(from, to, loc);
			par1Sender.sendMessage(plugin.getPrefix() + Util.maskedStringReplace(
					plugin.getMessage("Successtp"),
					new String[][]{
						{"%from", from.getDisplayName()},
						{"%to", ((to != null)?to.getDisplayName():buildLocationStr(loc))}
					}));
			return true;
		}
	}
	private void teleport(Player par1From, Player par2To, Location par3Loc){
		if(par2To != null){
			par1From.teleport(par2To);
		}else{
			par1From.teleport(par3Loc);
		}
	}
	private String buildLocationStr(Location par1Loc){
		StringBuilder var1 = new StringBuilder();
		var1.append(par1Loc.getX());
		var1.append(" ");
		var1.append(par1Loc.getY());
		var1.append(" ");
		var1.append(par1Loc.getZ());
		return var1.toString();
	}
}
