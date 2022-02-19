package fr.jejeoh.practice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

	private Base main;
	
	
	public CommandKit(Base main) {
		this.main = main;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(string.equalsIgnoreCase("kiteditor")) {
				player.openInventory(main.getInstance().inv.getInv(player, "kiteditor"));

				
			}if(string.equalsIgnoreCase("test")) {		
				
				Bukkit.dispatchCommand(player, "tp " + player.getName() + " -248 74 938");
				
			}if(string.equalsIgnoreCase("setarene")) {
				main.inv.onAddArene(player);
			}if(string.equalsIgnoreCase("getarene")) {
				main.getar = 1;
				player.openInventory(main.inv.onArene(1, player));
			}
		}return false;
	}

}
