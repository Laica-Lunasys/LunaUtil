package LunaUtil.Core;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import LunaUtil.Cooperates.BKCommonLibCooperate;
import LunaUtil.Core.Config.ConfigurationManager;
import LunaUtil.Enum.EnumCommandList;
import LunaUtil.Listener.PlayerListener;
import LunaUtil.Util.Util;

import com.bergerkiller.bukkit.common.internal.CommonPlugin;

public class LunaUtil extends JavaPlugin {

	public Logger log;
	public static LunaUtil plugin;
	public static String PluginName = "LunaUtil";
	/* パーミッションの定義  */
	public final String PermGM = "LunaUtil.gm";
	public final String PermFLY = "LunaUtil.fly";
	public final String PermTP = "LunaUtil.tp";
	public final String PermSPAWN = "LunaUtil.Member.Spawn";
	public final String PermSPAWNSET = "LunaUtil.Spawn.Set";
	public final String PermJailSet = "LunaUtil.Jail.Set";
	public final String PermJailAdd = "LunaUtil.Jail.Add";
	public final String PermJailRemove = "LunaUtil.Jail.Remove";
	public final String PermJailExempt = "LunaUtil.Jail.Exempt";
	public final String PermSH = "LunaUtil.Shell";
	public final String PermWorldList = "LunaUtil.World.list";
	public final String PermWorldGen = "LunaUtil.World.add";
	public final String PermWorldDel = "LunaUtil.World.del";
	public final String PermWorldGo = "LunaUtil.World.go";
	/* シェルを実行できるプレイヤ－(canExecuteOPがfalseの時のみ有効) */
	public String ShellPlayer;
	/* シェルをOPが実行できるかのフラグ */
	public boolean canExecuteOP;
	/* シェル使用判定にOPではなくパーミッションを使うか否か(要するにPermissionsEXが入っているかどうか) */
	public boolean canUsePermission;
	ConfigurationManager cfg ;
	public ConfigurationManager msg ;
	static ConfigurationManager country;
	public ConfigurationManager jail;
	public BKCommonLibCooperate bkcommonlib;;

	@Override
	public void onEnable() {
		plugin = this;
		this.log = this.getLogger();
		cfg = new ConfigurationManager(this);
		country = new ConfigurationManager(this,"country.yml");
		jail = new ConfigurationManager(this,"jail.yml");
		String var1 = Util.BuildString(false, "messages_",cfg.getString("lang"),".yml");
		msg = new ConfigurationManager(this,var1);
		InitConfig();
		Plugin temp = getServer().getPluginManager().getPlugin("PermissionEx");
		 if ( temp != null && temp instanceof PermissionsEx) {
			 canUsePermission = true;
		 }else{
			 canExecuteOP = cfg.getBoolean("Shell.isOP");
		 }
		 temp = getServer().getPluginManager().getPlugin("BKCommonLib");
		 if ( temp != null && temp instanceof CommonPlugin) {
			 log.info("BKCommonLib Cooperate Enable.");
			 bkcommonlib = new BKCommonLibCooperate(this, (CommonPlugin)temp);
		 }else{
			 bkcommonlib = null;
			 log.info("BKCommonLib Cooperate Disable.");
		 }
		new PlayerListener(this);
		plugin.saveConfig();
		for(EnumCommandList t : EnumCommandList.values()) {
			this.getCommand(t.getCommandLine()).setExecutor(t.getCommandExecutor());
		}
		this.log.info(PluginName + " has been enabled!");
	}

	/**
	 * プラグインのJARファイルのFileを返す
	 * @return JARのFileインスタンス
	 */
	public File getPluginJarFile() {
		return plugin.getFile();
	}

	/**
	 * メッセージをコンフィグから読み取って返す
	 * @param par1Key コンフィグのキー
	 * @return メッセージ
	 */
	public String getMessage(String par1Key) {
		return msg.getString(par1Key);
	}

	/**
	 * コマンドのPrefixを返す
	 * @return PrefixをマスキングリプレースしたString
	 */
	public String getPrefix(){
		return Util.maskedStringReplace(cfg.getString("Prefix"),null);
	}
	/**
	 * 国名に対応する国名を返す
	 * @param par1Cnt 国名(英語のみ可)
	 * @return マスキングリプレース済みの国名(コンフィグにあればそちらを参照)
	 */
	public static String getCountryString(String par1Cnt){
		String var1 = country.getString("country." + par1Cnt.toLowerCase());
		return Util.maskedStringReplace((var1 != null ? var1 : par1Cnt),null);
	}
	@Override
	public void onDisable() {
		this.log.info(PluginName + " has been disabled.");
	}
	/**
	 * メッセージの送信を行う
	 * @param par1Sender メッセージの送信先
	 * @param par2String メッセージ(カラーコード可)
	 */
	public static void Message(
			CommandSender par1Sender,
			String par2String){
		if(par1Sender != null){
			par1Sender.sendMessage(Util.maskedStringReplace(par2String, null));
		}else{
			plugin.log.info(Util.maskedStringReplace(par2String, null));
		}
	}
	/**
	 * マスク付きメッセージの送信を行う
	 * @param par1Sender メッセージの送信先
	 * @param par2String メッセージ(カラーコード/マスク可)
	 * @param par3Masks  マスクの指定
	 */
	public static void Message(
			CommandSender par1Sender,
			String par2String,
			String[][] par3Masks){
			Message(par1Sender,Util.maskedStringReplace(par2String, par3Masks));
	}
	/**
	 * メインコンフィグを取得する
	 * @return コンフィグのインスタンス
	 */
	public ConfigurationManager getcfg(){
		return cfg;
	}
	/**
	 * コンフィグのイニシャライズを行う
	 */
	private void InitConfig(){
		ShellPlayer = cfg.getString("Shell.Player");
	}
}