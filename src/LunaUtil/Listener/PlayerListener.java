/**
 *
 */
package LunaUtil.Listener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.Util;

/**
 * @author squarep
 *
 */
public class PlayerListener implements Listener{
	LunaUtil plugins;
	public PlayerListener(LunaUtil plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
		plugins = plugin;
	}
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event){
		final String var1 = Util.getIp(event.getPlayer());
		event.setJoinMessage(Util.maskedStringReplace(
				plugins.msg.getString("LoginMsg"),
								new String[][]{
								{"%player", event.getPlayer().getDisplayName()},
								{"%country", Util.IP2CNT(var1)}
				}));
		if(plugins.getcfg().getBoolean("isJoinAndSpawn")){
			event.getPlayer().teleport(plugins.getcfg().getWorld("SpawnWorld").getSpawnLocation());
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		event.setQuitMessage(Util.maskedStringReplace(
			    plugins.msg.getString("ExitMsg"),
		        new String[][]{
		        {"%player", event.getPlayer().getDisplayName()}
			    }));}
	public void onPlayerRespawn(PlayerRespawnEvent event){
		List<String> var1 = plugins.jail.getStringList("Players");
		for(String t : var1){
			if(event.getPlayer().getName().equalsIgnoreCase(t)){
				if(plugins.jail.getString("Location.world") == null){
					return;
				}
				Location var2 = plugins.jail.getLocation("Location");
				event.setRespawnLocation(var2);
				break;
			}
		}

	}
}
