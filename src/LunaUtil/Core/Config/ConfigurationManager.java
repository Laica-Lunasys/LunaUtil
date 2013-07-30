package LunaUtil.Core.Config;

import LunaUtil.Core.LunaUtil;


public class ConfigurationManager extends FileSetConfiguration {
	FileSetConfiguration cfg;
	public ConfigurationManager(LunaUtil par1Plugin){
		super(par1Plugin, "config.yml");
		cfg = super.Instance;
	}
	public ConfigurationManager(LunaUtil par1Plugin, String par2Fname){
		super(par1Plugin, par2Fname);
		cfg = super.Instance;
	}
}
