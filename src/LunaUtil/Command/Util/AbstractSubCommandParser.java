package LunaUtil.Command.Util;

import LunaUtil.Command.Util.SubCommandParser.IParser;


public abstract class AbstractSubCommandParser<T> implements IParser{
	String Cmd;
	public AbstractSubCommandParser(String par1Cmd){
		Cmd = par1Cmd;
	}
	@Override
	public String getCommand() {
		return Cmd;
	}
	public abstract T getDefault();
	public abstract T getValue();
	public abstract boolean hasvalue();
}