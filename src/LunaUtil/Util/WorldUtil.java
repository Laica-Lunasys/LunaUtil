package LunaUtil.Util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import LunaUtil.Core.LunaUtil;

public class WorldUtil {
	LunaUtil plugin;
	Long Seed = null;
	Map<String, World>worlds = new HashMap<String,World>();
	public WorldUtil(LunaUtil par1Plugin){
		this(par1Plugin, (new Random()).nextLong());
	}
	public WorldUtil(LunaUtil par1Plugin, long par2Seed){
		plugin = par1Plugin;
		Seed = par2Seed;
		for(World t : plugin.getServer().getWorlds()){
			worlds.put(t.getName(), t);
		}
	}
	/**
	 * ワールド生成基幹メソッド
	 * @param par1WorldName				ワールド名
	 * @param par2Env					ワールドの種類
	 * @param par3IsGenStructures		構造物の生成可否
	 * @param par4Type					ワールドタイプ
	 * @return							成功したらtrue,失敗であればfalse
	 */
	public boolean genWorld(
			String par1WorldName,
			Environment par2Env,
			boolean par3IsGenStructures,
			WorldType par4Type){
		WorldCreator var1 = WorldCreator.name(par1WorldName);
		var1.environment(par2Env);
		var1.generateStructures(par3IsGenStructures);
		var1.type(par4Type);
		var1.seed(Seed.longValue());
		World var2 = null;
		if((var2 = plugin.getServer().createWorld(var1))!=null){
			worlds.put(par1WorldName, var2);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * ワールド削除メソッド
	 * @param par1WorldName			ワールド名
	 * @return						成功したらtrue,失敗であればfalse
	 */
	public boolean delWorld(String worldname) {
		File BaseFolder = new File(Bukkit.getServer().getWorld("world").getWorldFolder().getParent());
		plugin.log.info(BaseFolder.getAbsolutePath() + File.separator + worldname);
		return delete(new File(BaseFolder.getAbsolutePath() + File.separator + worldname));
	}
	private static boolean delete(File folder) {
		if (folder.isDirectory()) {
			for (File f : folder.listFiles()) {
				if (!delete(f)) return false;
			}
		}
		return folder.delete();
	}
	public boolean isUnload(String par1WorldName){
		return plugin.getServer().getWorld(par1WorldName) == null;
	}
	public World getWorld(World par1World) {
		return getWorld(par1World.getName());
	}
	public World getWorld(String par1WorldName) {
		if(worlds.containsKey(par1WorldName)){
			return worlds.get(par1WorldName);
		}
		throw new RuntimeException("World Notfound " + par1WorldName);
	}
	public List<World> getWorlds() {
		List<World> var1 = new LinkedList<World>();
		var1.addAll(worlds.values());
		return var1;
	}
	public boolean isLoaded(String worldname) {
		return getWorld(worldname) != null;
	}
	public boolean worldExists(String worldname) {
		return worldname != null && com.bergerkiller.bukkit.common.utils.WorldUtil.isLoadableWorld(worldname);
	}
}
