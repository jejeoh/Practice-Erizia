package fr.jejeoh.practice.Groupe;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;

public class CommandeGroupe implements CommandExecutor {

	//private Base main;
	private Groupe gr;

	public CommandeGroupe(Base main, Groupe gr) {
	//	this.main = main;
		this.gr = gr;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length > 0) {
				
				
				if(args[0].equalsIgnoreCase("invite")) {
					if(args.length < 2) {
						player.sendMessage("§c§lLa commande est /groupe invite <player>");
						return false;
					}			
					Player play = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(play)) {
						player.sendMessage("§c§lLe joueur n'est pas connecter !");
						return false;
					}
					if(gr.getGrp.containsKey(play)) {
						player.sendMessage("§c§lLe joueur est déjà dans un groupe !");
						return false;
					}
					if(gr.getGrp.containsKey(player)) {
						if(!gr.chef.containsValue(player)) {
							player.sendMessage("§c§lTu es déjà dans un groupe et tu n'es pas le chef !");
							return false;
						}
					}
					if(player.getName().equalsIgnoreCase(play.getName())) {
						player.sendMessage("§c§LTu ne peux pas t'auto inviter");
						return false;
					}
						gr.onInvite(player, play);
						return true;
				}
				
				
				if(args[0].equalsIgnoreCase("o2f8jn158")) {
					if(args.length < 2) {
						return false;
					}			
					Player play = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(play)) {
						player.sendMessage("§c§lLe joueur n'est pas connecter !");
						return false;
					}
					if(gr.getGrp.containsKey(play)) {
						if(!gr.chef.containsValue(play)) {
							player.sendMessage("§c§lLe jourut n'est plus chef su groupe !");
							return false;
					}
					}
					if(gr.getGrp.containsKey(player)) {
						player.sendMessage("§c§lTu es déjà dans un groupe !");
						return false;
					}
					if(player.getName().equalsIgnoreCase(play.getName())) {
						player.sendMessage("§c§LTu ne peux pas t'auto inviter");
						return false;
					}
						gr.onJoin(play, player);
						return true;
					
				}
				
				
				
				if(args[0].equalsIgnoreCase("list")) {
					if(!gr.getGrp.containsKey(player)) {
						player.sendMessage("§c§lTu n'es pas dans un groupe !");
						return false;
					}
					gr.onGet(player);
				}
				if(args[0].equalsIgnoreCase("leave")) {
					if(!gr.getGrp.containsKey(player)) {
						player.sendMessage("§c§lTu n'es pas dans un groupe !");
						return false;
					}
					gr.onLeave(player);
				}
				
				if(args[0].equalsIgnoreCase("kick")) {
					if(args.length < 2) {
						player.sendMessage("§c§lLa commande est /groupe kick <player>");
						return false;
					}			
					Player play = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(play)) {
						player.sendMessage("§c§lLe joueur n'est pas connecter !");
						return false;
					}
					if(!gr.getGrp.containsKey(play)) {
						player.sendMessage("§c§lLe joueur n'est pas dans un groupe !");
						return false;
					}
					if(gr.getGrp.get(play) != gr.getGrp.get(player)) {
						player.sendMessage("§c§lLe joueur n'est pas dans ton groupe !");
						return false;
					}
					if(gr.getGrp.containsKey(player)) {
						if(!gr.chef.containsValue(player)) {
							player.sendMessage("§c§lTu n'es pas le chef du groupe !");
							return false;
						}
					}
					if(player.getName().equalsIgnoreCase(play.getName())) {
						player.sendMessage("§c§LTu ne peux pas t'auto kick");
						return false;
					}
						gr.onKick(play);
						return true;
				}
				
				if(args[0].equalsIgnoreCase("lead")) {
					if(args.length < 2) {
						player.sendMessage("§c§lLa commande est /groupe lead <player>");
						return false;
					}			
					Player play = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(play)) {
						player.sendMessage("§c§lLe joueur n'est pas connecter !");
						return false;
					}
					if(!gr.getGrp.containsKey(play)) {
						player.sendMessage("§c§lLe joueur n'est pas dans un groupe !");
						return false;
					}
					if(gr.getGrp.get(play) != gr.getGrp.get(player)) {
						player.sendMessage("§c§lLe joueur n'est pas dans ton groupe !");
						return false;
					}
					if(gr.getGrp.containsKey(player)) {
						if(!gr.chef.containsValue(player)) {
							player.sendMessage("§c§lTu n'es pas le chef du groupe !");
							return false;
						}
					}
					if(player.getName().equalsIgnoreCase(play.getName())) {
						player.sendMessage("§c§LTu ne peux pas t'auto lead");
						return false;
					}
						gr.onLead(play);
						return true;
				}
			}
		}else {
			
		}
		
		return false;
	}

}
