package LunaUtil.Command.Parser;

import org.bukkit.World.Environment;
import org.bukkit.WorldType;

import LunaUtil.Command.Util.AbstractSubCommandParser;

public class WorldSubCommands {
	public static class EnvironmentParser extends AbstractSubCommandParser<Environment>{
		private Environment env = Environment.NORMAL;
		public EnvironmentParser(String par1Cmd) {
			super(par1Cmd);
		}
		@Override
		public void parse(String par1Str) {
			int ID;
			if(par1Str.equalsIgnoreCase("normal")){
				ID = 0;
			}else if(par1Str.equalsIgnoreCase("nether")){
				ID = -1;
			}else if(par1Str.equalsIgnoreCase("end") || 
					 par1Str.equalsIgnoreCase("theend") ||
					 par1Str.equalsIgnoreCase("the_end")){
				ID = 1;
			}else{
				ID = 0;
			}
			env = Environment.getEnvironment(ID);
		}
		@Override
		public Environment getValue() {
			return env;
		}
		@Override
		public boolean hasvalue() {
			return true;
		}
		@Override
		public Environment getDefault() {
			return Environment.NORMAL;
		}
	}
	
	public static class StructureParser extends AbstractSubCommandParser<Boolean>{
		
		public StructureParser(String par1Cmd) {
			super(par1Cmd);
		}
		private Boolean isGenStructure = false;
		@Override
		public void parse(String par1Str) {
			if(par1Str.matches("^*(false|off)")){
				isGenStructure = false;
			}else{
				isGenStructure = true;
			}
		}
		@Override
		public Boolean getValue() {
			return isGenStructure;
		}
		@Override
		public boolean hasvalue() {
			return true;
		}
		@Override
		public Boolean getDefault() {
			return true;
		}
	}
	
	public static class WorldTypeParser extends AbstractSubCommandParser<WorldType>{
		
		public WorldTypeParser(String par1Cmd) {
			super(par1Cmd);
		}
		private WorldType type = WorldType.NORMAL;
		@Override
		public void parse(String par1Str) {
			if(par1Str.equalsIgnoreCase("large")){
				type = WorldType.LARGE_BIOMES;
			}else if(par1Str.equalsIgnoreCase("flat")){
				type = WorldType.FLAT;
			}else{
				type = WorldType.NORMAL;
			}
		}
		@Override
		public WorldType getValue() {
			return type;
		}
		@Override
		public boolean hasvalue() {
			return true;
		}
		@Override
		public WorldType getDefault() {
			return WorldType.NORMAL;
		}
	}
}