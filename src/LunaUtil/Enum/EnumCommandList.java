package LunaUtil.Enum;

import org.bukkit.command.CommandExecutor;

import LunaUtil.Command.CmdFly;
import LunaUtil.Command.CmdGamemode;
import LunaUtil.Command.CmdJail;
import LunaUtil.Command.CmdShell;
import LunaUtil.Command.CmdTransPort;
import LunaUtil.Command.CmdWorld;
import LunaUtil.Core.LunaUtil;

public enum EnumCommandList {
	TP("tp", CmdTransPort.class),
	T2P("t2p", CmdTransPort.class),
	TPH("tphere", CmdTransPort.class),
	TPLOC("tploc", CmdTransPort.class),
	SHELL("shell", CmdShell.class),
	JAIL("jail", CmdJail.class),
	GM("gm", CmdGamemode.class),
	WORLD("world", CmdWorld.class),
	FLY("fly", CmdFly.class);
   Class<? extends CommandExecutor> CommandClass;
   String cmdline;

   private EnumCommandList(String par1Cmdline, Class<? extends CommandExecutor> par2Class) {
      this.CommandClass = par2Class;
      this.cmdline = par1Cmdline;
   }

   public CommandExecutor getCommandExecutor() {
      try {
         return this.CommandClass.getConstructor(new Class[]{LunaUtil.class})
        		 .newInstance(new Object[]{LunaUtil.plugin});
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public String getCommandLine() {
      return this.cmdline;
      
   }
   
   
}
