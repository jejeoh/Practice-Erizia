package fr.jejeoh.practice.Commandes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;

public class CommandeDuel implements CommandExecutor {
	
	private Base main;

	public CommandeDuel(Base main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length >= 1) {
				if(player.getName().equalsIgnoreCase(args[0])) {
					player.sendMessage("§c§lVous ne pouvez pas vous auto invitez !");
					return false;
				}
				
				Player play = Bukkit.getPlayer(args[0]);
				
				if(Bukkit.getOnlinePlayers().contains(play)) {
					if(main.duel.containsKey(play)) {
						player.sendMessage("§c" + play.getName() + " est déjà demandé !");
						return false;
					}
					if(main.play.containsKey(play)) {
						player.sendMessage("§c" + play.getName() + " est déjà demandé !");
					}
					if(main.duel.containsKey(player)) {
						player.sendMessage("§c Tu es déjà en demande !");
						return false;
					}
					if(main.duel.containsValue(play)) {
						player.sendMessage("§c" + play.getName() + " est déjà en demande !");
						return false;
					}
					if(main.duel.containsKey(player)) {
						player.sendMessage("§c Tu est déjà demandé !");
						return false;
					}
					if(main.play.containsKey(player)) {
						player.sendMessage("§c Tu est déjà demandé !");
					}
					
					player.openInventory(main.inv.isDuel(player , play));
					
					return true;
					
					/**
					 * main.duel.put(play, player);
					 * main.duelkit.put(play, args[1]);
					 * player.sendMessage("§a§lL'invitation de " + play.getName() + " a bien été faite !");
					 * play.openInventory(main.inv.getDuel(play));
					 * 	return true;
					**/
					
					
				}else {
					player.sendMessage("§6§lJoueur non connecter");
				}
			}else {
				player.sendMessage("§c§lLa commande est /duel <joueur>");
			}
		}
		
		return false;
	}

}
