package LunaUtil.Cooperates;

import org.bukkit.plugin.Plugin;

public abstract class PluginsCooperate<T> {
	protected T CooperatePlugin;
	protected Plugin plugin;
	public PluginsCooperate(Plugin par1Plugin, T par2Plugin){
		plugin = par1Plugin;
		CooperatePlugin = par2Plugin;
	}
	protected abstract void Init();
	protected boolean isnull(Object par1Obj){
		return par1Obj==null;
	}
}
