package LunaUtil.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;
import LunaUtil.Util.InputStreamThread;
import LunaUtil.Util.Util;

public class CmdShell implements CommandExecutor {

	LunaUtil plugin;
	String SOStr,ERStr;


	public CmdShell(LunaUtil par1Plugin) {
		this.plugin = par1Plugin;
	}
	@Override
	@SuppressWarnings({ })
	public boolean onCommand(CommandSender par1Sender, Command par2Command, String par3CmdLine, String[] par4args) {
		if(!System.getProperty("os.name").matches("^*(Linux|BSD)*")){
			par1Sender.sendMessage(plugin.getPrefix() + Util.maskedStringReplace(
					plugin.getMessage("notExecOS"),
					new String[][]{
						{"%OS", System.getProperty("os.name")},
					}));
		}
		if (par4args.length < 1){
			return false;
		}
		if(par1Sender instanceof Player){
			// パーミッションを使う場合	
			if (plugin.canUsePermission) {
				// パーミッションチェック
				if (Util.hasPerm(plugin.PermSH, (par1Sender), plugin)) {
					return false;
				}
			// パーミッションを使わない場合
			}else{
				// OPチェック
				if(!((plugin.canExecuteOP && ((Player) par1Sender).isOp()))){
					String var1 = plugin.getPrefix() +
							Util.maskedStringReplace(
									this.plugin.getMessage("notExecShell"),
									new String[][] { {"%player", par1Sender.getName()} });
					par1Sender.sendMessage(var1);
					return false;
				// どれでもなければユーザーチェック
				}else if (!((Player)par1Sender).
						getDisplayName().
						equalsIgnoreCase(plugin.ShellPlayer)){
					String var1 = plugin.getPrefix() +
							Util.maskedStringReplace(
									this.plugin.getMessage("notExecShell"),
									new String[][] { {"%player", par1Sender.getName()} });
					par1Sender.sendMessage(var1);
					return false;
				}
			}
		}
		List<String> var2 = new ArrayList<String>();
		var2 = Arrays.asList(par4args);
		System.out.println("Args : " + var2.get(0));
		execute(new ProcessBuilder(var2));
		par1Sender.sendMessage(SOStr);
		par1Sender.sendMessage(ERStr);
		return true;
	}
	
	private void execute(ProcessBuilder par1Pb){
		Process p;
		try {
			p = par1Pb.start();

		//InputStreamのスレッド開始
		InputStreamThread it = new InputStreamThread(p.getInputStream());
		InputStreamThread et = new InputStreamThread(p.getErrorStream());
		it.start();
		et.start();

		//プロセスの終了待ち
		p.waitFor();

		//InputStreamのスレッド終了待ち
		it.join();
		et.join();

		System.out.println("returned ： " + p.exitValue());
		StringBuilder var1 = new StringBuilder();
		//標準出力の内容を出力
		for (String s : it.getStringList()) {
			var1.append(s);
		}
		SOStr = var1.toString();
		var1 = new StringBuilder();
		//標準エラーの内容を出力
		for (String s : et.getStringList()) {
			var1.append(s);
		}
		ERStr = var1.toString();
		} catch (IOException e) {
			System.out.println("Can't Execute Command.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
