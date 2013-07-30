package LunaUtil.Command.Util;

import java.util.HashMap;
import java.util.Map;

public class SubCommandParser{
	String[] Src;
	Map<String, AbstractSubCommandParser<?>> parsers;
	int Index;
	String Other;
	public SubCommandParser(String[] par1Str, AbstractSubCommandParser<?> par2Parser[]){
		Src = par1Str;
		parsers = new HashMap<String, AbstractSubCommandParser<?>>();
		for(AbstractSubCommandParser<?> t : par2Parser){
			parsers.put(t.getCommand(), t);
		}
	}
	public void parse(){
		for (int i = 0; i < Src.length; i++){
			if(parsers.containsKey(Src[i])){
				parsers.get(Src[i]).parse(Src[++i]);
			}else{
				Other = Src[i];
			}
		}
	}
	public String getOther(){
		return Other;
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String par1Key, Class<T> par2Class){
		return  (T) parsers.get(par1Key).getValue();
	}
	public interface IParser{
		public String getCommand();
		public void parse(String par1Str);
	}
}
