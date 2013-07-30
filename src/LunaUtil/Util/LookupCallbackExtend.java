package LunaUtil.Util;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import LunaUtil.Core.LunaUtil;

import com.mcbans.firestar.mcbans.MCBans;
import com.mcbans.firestar.mcbans.api.data.PlayerLookupData;
import com.mcbans.firestar.mcbans.callBacks.LookupCallback;

public class LookupCallbackExtend  extends LookupCallback{
	Plugin plugin;
	String LookupMsg;
	boolean isMsg;
	CommandSender sender;
	PlayerLookupData var1;
	public LookupCallbackExtend(MCBans par1MCBans, CommandSender par2Sender, Plugin par3Plugin, boolean par4isMsg){
		super(par1MCBans, par2Sender);
		plugin = par3Plugin;
		sender = par2Sender;
		LookupMsg = Util.BuildString(true, ((LunaUtil)plugin).getcfg().getStringList("MCBans.Lookup").toArray(new String[]{}));
		isMsg = par4isMsg;
	}
	public LookupCallbackExtend(MCBans par1MCBans, CommandSender par2Sender, Plugin par3Plugin){
		this(par1MCBans, par2Sender, par3Plugin, true);
	}
	@Override
	public void success(final PlayerLookupData data){
		var1 = data;
        if (isMsg) {
			StringBuilder Global = new StringBuilder();
			StringBuilder Local = new StringBuilder();
			StringBuilder Other = new StringBuilder();
			if (data.getGlobals().size() > 0) {
				for (String record : data.getGlobals()) {
					Global.append(record);
					Global.append("\n");
				}
			}
			if (data.getLocals().size() > 0) {
				for (String record : data.getLocals()) {
					Local.append(record);
					Local.append("\n");
				}
			}
			if (data.getOthers().size() > 0) {
				for (String record : data.getOthers()) {
					Other.append(record);
					Other.append("\n");
				}
			}
			plugin.getServer().broadcastMessage(Util.maskedStringReplace(LookupMsg, new String[][] {
					{ "%REP", ((Double) data.getReputation()).toString() },
					{ "%Global", Global.toString() },
					{ "%Local", Local.toString() },
					{ "%d", ((Integer) data.getTotal()).toString() },
					{ "%s", Other.toString() },
			}));
		}
    }
	public PlayerLookupData getData(){
		return var1;
	}
}
