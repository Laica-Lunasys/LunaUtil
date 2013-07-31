package LunaUtil.Command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Command.Parser.WorldSubCommands.EnvironmentParser;
import LunaUtil.Command.Parser.WorldSubCommands.StructureParser;
import LunaUtil.Command.Parser.WorldSubCommands.WorldTypeParser;
import LunaUtil.Command.Util.AbstractSubCommandParser;
import LunaUtil.Command.Util.SubCommandParser;
import LunaUtil.Core.LunaUtil;
import LunaUtil.Core.Task;
import LunaUtil.Util.Util;
import LunaUtil.Util.WorldUtil;

public class CmdWorld implements CommandExecutor {

	LunaUtil plugin;
	WorldUtil worldutil;

	public CmdWorld(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
		worldutil = new WorldUtil(plugin);
	}
	@Override
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3CmdLine, final String[] par4args) {
		Player player = null;
		if(par4args.length < 1){
			par1Sender.sendMessage("Invalid Argument.");
			return false;
		}
		if(par4args[0].equalsIgnoreCase("gen")){
			if(!Util.hasPerm(plugin.PermWorldGen, par1Sender, plugin)){
				return false;
			}
			SubCommandParser p = new SubCommandParser(par4args, new AbstractSubCommandParser<?>[]{
					new EnvironmentParser("env"),
					new StructureParser("structure"),
					new WorldTypeParser("type"),
			});
			p.parse();
			LunaUtil.Message(
					par1Sender, 
					(worldutil.genWorld(p.getOther(),
							p.get("env", Environment.class),
							p.get("structure", Boolean.class),
							p.get("type", WorldType.class))? 
									plugin.msg.getString("World.Create") : 
									plugin.msg.getString("World.FailedCreate")), 
					new String[][]{
						{"%world", p.getOther() },
					}
			);
		}else if (par4args[0].equalsIgnoreCase("remove")){
			if(!Util.hasPerm(plugin.PermWorldDel, par1Sender, plugin)){
				return false;
			}
			//if(!worldutil.isUnload(par4args[1])){
			//	Bukkit.getServer().unloadWorld(par4args[1], false);
			//}
			/*LunaUtil.Message(
					par1Sender, 
					( ? 
									plugin.msg.getString("World.Remove") : 
									plugin.msg.getString("World.FailedRemove")), 
					new String[][]{
						{"%world", par4args[1]},
					}
			);*/
			//Task.delete(par1Sender, par4args[1]);
			
			String worldname = par4args[1];
			if (!worldutil.isLoaded(worldname)) {
				Task.delete(par1Sender, worldname);
			} else {
				LunaUtil.Message(par1Sender, "&cWorld is loaded, please unload the world first!");
			}
		}if(par4args[0].equalsIgnoreCase("unload")){
			Bukkit.getServer().unloadWorld(par4args[1], false);
		}else if (par4args[0].equalsIgnoreCase("go")){
			if(!Util.hasPerm(plugin.PermWorldGo, par1Sender, plugin)){
				return false;
			}
			if(par4args.length > 2){ // /world go Player Worldの場合
				if((player = Util.findPlayer(par4args[1], plugin)) == null){
					return false;
				}
				player.teleport(worldutil.getWorld(par4args[2]).getSpawnLocation());
			}else{					// /world go Worldの場合
				if(!(par1Sender instanceof Player)) {
					this.plugin.getLogger().info("Can\'t execute this command on console.");
					return false;
				}
				player = ((Player)par1Sender);
				player.teleport(worldutil.getWorld(par4args[1]).getSpawnLocation());
			}
		}else if (par4args[0].equalsIgnoreCase("list")){
			if(!Util.hasPerm(plugin.PermWorldList, par1Sender, plugin)){
				return false;
			}
			StringBuilder var1 = new StringBuilder();
			for(World t : worldutil.getWorlds()){
				var1.append(Util.maskedStringReplace(plugin.msg.getString("World.List"), new String[][]{
					{"%world", t.getName()},
					{"%type", t.getEnvironment().toString()},
				}));
				var1.append("\n");
			}
			LunaUtil.Message(par1Sender, var1.toString());
		}else if (par4args[0].equalsIgnoreCase("list")){
			if(!Util.hasPerm(plugin.PermWorldList, par1Sender, plugin)){
				return false;
			}
			StringBuilder var1 = new StringBuilder();
			for(World t : worldutil.getWorlds()){
				var1.append(Util.maskedStringReplace(plugin.msg.getString("World.List"), new String[][]{
					{"%world", t.getName()},
					{"%type", t.getEnvironment().toString()},
				}));
				var1.append("\n");
			}
			LunaUtil.Message(par1Sender, var1.toString());
		}
		return true;
	}
}
