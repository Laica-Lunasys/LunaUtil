package LunaUtil.Cooperates;

import LunaUtil.Core.LunaUtil;

import com.bergerkiller.bukkit.common.internal.CommonPlugin;
import com.bergerkiller.bukkit.common.utils.WorldUtil;

public class BKCommonLibCooperate extends PluginsCooperate<CommonPlugin> {

	private static CommonPlugin BKCommonLibPlugin = null;
	public BKCommonLibCooperate(LunaUtil par1Plugin, CommonPlugin par2BKCommonLib) {
		super(par1Plugin, par2BKCommonLib);
	}
	@Override
	protected void Init(){
		BKCommonLibPlugin = CommonPlugin.getInstance();
	}
	
	public WorldUtil getWorldUtil(){
		if(isnull(BKCommonLibPlugin)){
			Init();
		}
		return new WorldUtil();
	}

}
