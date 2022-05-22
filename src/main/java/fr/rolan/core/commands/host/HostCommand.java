package fr.rolan.core.commands.host;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.rolan.api.commands.Commands;
import fr.rolan.core.commands.host.cmd.AddCommand;
import fr.rolan.core.commands.host.cmd.ConfigCommand;
import fr.rolan.core.commands.host.cmd.Effect;
import fr.rolan.core.commands.host.cmd.HealCommand;
import fr.rolan.core.commands.host.cmd.HelpCommand;
import fr.rolan.core.commands.host.cmd.KillCommand;
import fr.rolan.core.commands.host.cmd.OtherCommand;
import fr.rolan.core.commands.host.cmd.RemoveCommand;
import fr.rolan.core.commands.host.cmd.ReviveCommand;
import fr.rolan.core.commands.host.cmd.SayCommand;
import fr.rolan.core.commands.host.cmd.SelectHostCommand;
import fr.rolan.core.commands.host.cmd.TpCommand;
import fr.rolan.core.commands.host.cmd.UnbreakbleCommand;

public class HostCommand implements TabExecutor {
	private final Map<String, Commands> listCommands = new HashMap<String, Commands>();
	
	public HostCommand() {
		this.listCommands.put("config", new ConfigCommand());
		this.listCommands.put("help", new HelpCommand());
		this.listCommands.put("say", new SayCommand());
		HealCommand healCommand = new HealCommand();
		this.listCommands.put("heal", healCommand);
		this.listCommands.put("h", healCommand);
		UnbreakbleCommand unbreakbleCommand = new UnbreakbleCommand();
		this.listCommands.put("unbreakble", unbreakbleCommand);
		this.listCommands.put("u", unbreakbleCommand);
		this.listCommands.put("kill", new KillCommand());
		this.listCommands.put("e", new Effect());
		this.listCommands.put("tp", new TpCommand());
		this.listCommands.put("add", new AddCommand());
		this.listCommands.put("remove", new RemoveCommand());
		this.listCommands.put("host", new SelectHostCommand());
		this.listCommands.put("revive", new ReviveCommand());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> temp = new ArrayList<>(this.listCommands.keySet());
	    if(args.length == 0)
	    	return temp; 
	    if(args.length == 1) {
	    	for(int i = 0; i < temp.size(); i++) {
	    		for(int j = 0; j < ((String)temp.get(i)).length() && j < args[0].length(); j++) {
	    			if(((String)temp.get(i)).charAt(j) != args[0].charAt(j)) {
	    				temp.remove(i);
	    				i--;
	    				break;
	    			} 
	    		} 
	    	} 
	    	return temp;
	    }
	    return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(args.length == 0)
			return true;
		((Commands) this.listCommands.getOrDefault(args[0], new OtherCommand())).onCommand(sender, cmd, label, args);
		return false;
	}
}