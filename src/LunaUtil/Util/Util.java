package LunaUtil.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import LunaUtil.Core.LunaUtil;

public class Util {

	/**
	 * マスキングされた文字列のリプレースを行う
	 * @param par1Src   マスキングされた文字列
	 * @param par2Masks マスク部分の代替文字列
	 * @return マスキングリプレース済みの文字列
	 */
	public static String maskedStringReplace(String par1Src, String[][] par2Masks) {
		String var1 = par1Src;
		if(par2Masks != null){
			for(int i = 0; i < par2Masks.length; ++i) {
				var1 = var1.replaceAll(par2Masks[i][0], par2Masks[i][1]);
			}
		}
		var1 = var1.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
		return var1;
	}
	/**
	 * プレイヤーをサーバーから検索する
	 * @param par1Player プレイヤー名
	 * @param par2Plugin プラグインインスタンス
	 * @return プレイヤーのインスタンス
	 */
	public static Player findPlayer(String par1Player, LunaUtil par2Plugin){
		return findPlayer(par1Player, par2Plugin, null);
	}

	/**
	 * プレイヤーをサーバーから検索する
	 * @param par1Player プレイヤー名
	 * @param par2Plugin プラグインインスタンス
	 * @param par3Sender エラーメッセージを表示するCommandSenderの指定
	 * @return プレイヤーのインスタンス
	 */
	public static Player findPlayer(String par1Player, LunaUtil par2Plugin, CommandSender par3Sender){
		if(par2Plugin.getServer().getPlayer(par1Player) == null) {
			if(par3Sender != null){
				String mode1 = Util.maskedStringReplace(
						par2Plugin.getMessage("CFPlayer"),
				 new String[][]{
					 {"%player", par1Player}
				 });
				par3Sender.sendMessage(par2Plugin.getPrefix() + mode1);
			}
			return null;
		}
		return par2Plugin.getServer().getPlayer(par1Player);
	}
	/**
	 * パーミッションの有無を調べる
	 * @param par1Perm 調べたいパーミッション
	 * @param par2Player プレイヤーのインスタンス
	 * @param par3Plugin プラグインのインスタンス
	 * @return パーミッションの有無
	 */
	public static boolean hasPerm(String par1Perm, CommandSender par2Player, LunaUtil par3Plugin){
		return hasPerm(par1Perm, par2Player, par3Plugin, true);
	}
	/**
	 * パーミッションの有無を調べる
	 * @param par1Perm 調べたいパーミッション
	 * @param par2Player プレイヤーのインスタンス
	 * @param par3Plugin プラグインのインスタンス
	 * @param hasMessage メッセージを表示するか否か
	 * @return パーミッションの有無
	 */
	public static boolean hasPerm(String par1Perm, CommandSender par2Player, LunaUtil par3Plugin, boolean hasMessage){
		if(!(par2Player.hasPermission(par1Perm))
				&& !(par2Player.isOp())) {
			if(hasMessage){
				par2Player.sendMessage(par3Plugin.getPrefix() + Util.maskedStringReplace(
						par3Plugin.getMessage("DontHavePermission"),
						new String[][]{
							{"%player", par2Player.getName()},
							{"%perm", par1Perm}
						}));
			}
			return false;
		}
		return true;
	}

	/**
	 * IPアドレスから国名文字列を返す。
	 * @tips 国名文字列はマスキングリプレース済
	 * @param IP 検索対象のIPアドレス
	 * @return 国名文字列
	 */
	public static String IP2CNT(String IP){
	if(IP.startsWith("127.0.0.1") || IP.startsWith("192.168.") || !IP.matches("^(((\\d)|([1-9]\\d)|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d)|([1-9]\\d)|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))$")){
			return LunaUtil.getCountryString("local");
		}
		System.out.println("IP : " + IP);
		String ip = IP;
		String info;
		String api_key = "0661da511418add37fd168c89b52ddc51dce08b7fc65d43f3236811c2807ebc4";
		String str_url = "http://api.ipinfodb.com/v3/ip-city/?key=" + api_key + "&ip=" + ip;
		String[] final_info;
		try
		{
			URL url = new URL(str_url);
			URLConnection urlc = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			info = br.readLine();
			StringTokenizer st = new StringTokenizer(info,";");
			if(st.countTokens() == 10)
			{
				final_info = new String[st.countTokens()];
				for(int i = 0;st.hasMoreTokens();i++){
					final_info[i] = st.nextToken();
				}
				System.out.println(final_info[3]);
			}
			else{
				System.out.println("Invalid IP Address...!");
				return null;
			}
		}
		catch(Exception ex){
			System.out.println("Connection Problem...!");
			return null;
		}
            return LunaUtil.getCountryString(final_info[2]);
	}
	/**
	 * プレイヤーのIPアドレスを返す
	 * @param p プレイヤーのインスタンス
	 * @return IPアドレスの文字列
	 */
	public static String getIp(Player p) {
		InetSocketAddress IPAdressPlayer = p.getAddress();
		String sfullip = IPAdressPlayer.toString();
		String[] fullip;
		String[] ipandport;
		fullip = sfullip.split("/");
		String sIpandPort = fullip[1];
		ipandport = sIpandPort.split(":");
		String sIp = ipandport[0];
		return sIp;
	}
	/**
	 * JAR内にあるファイルを外部にコピーする
	 * @param jarFile        JARのFileインスタンス
	 * @param targetFile     ターゲットのFileインスタンス
	 * @param sourceFilePath 元ファイルのJAR内パス
	 * @param isBinary       バイナリーか否か
	 */
	public static void copyFileFromJar(
			File jarFile, File targetFile, String sourceFilePath, boolean isBinary) {

		InputStream is = null;
		FileOutputStream fos = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;

		File parent = targetFile.getParentFile();
		if ( !parent.exists() ) {
			parent.mkdirs();
		}

		try {
			JarFile jar = new JarFile(jarFile);
			ZipEntry zipEntry = jar.getEntry(sourceFilePath);
			is = jar.getInputStream(zipEntry);

			fos = new FileOutputStream(targetFile);

			if ( isBinary ) {
				byte[] buf = new byte[8192];
				int len;
				while ( (len = is.read(buf)) != -1 ) {
					fos.write(buf, 0, len);
				}

			} else {
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				writer = new BufferedWriter(new OutputStreamWriter(fos));

				String line;
				while ((line = reader.readLine()) != null) {
					writer.write(line);
					writer.newLine();
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if ( writer != null ) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( fos != null ) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
		}
	}
	/**
	 * 今までStringBuilderでちまちまやってたのを自動化するメソッド
	 * @param hasNewLine		それぞれのStringの後に改行を加えるか否か
	 * @param args				String型の可変数引数
	 * @return					StringBuilderで整形したString
	 */
	public static String BuildString(boolean hasNewLine, String... args){
		StringBuilder var1 = new StringBuilder();
		for(String t : args){
			var1.append(t);
			if(hasNewLine){
				var1.append("\n");
			}
		}
		return var1.toString();
	}
}
