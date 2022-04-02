package fr.jejeoh.practice.Commandes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;
import net.minecraft.server.v1_8_R3.EntityPlayer;

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
				player.sendMessage("§f§lEnd : " + main.end);
				player.sendMessage("§a§lArene : " + main.arensansjeu);
				player.sendMessage("§6§lSumo : " + main.sumosansjeu);
				main.duele.Particle(player, Color.BLUE);
				main.duele.Particle(player, Color.GREEN);
				main.duele.Particle(player, Color.RED);
				
				main.duele.Particles(player);
				
				/**int nj = 0;
				(int) Math.round( 1 + (Math.random() * (main.arensansjeu.size() - 1 )));
				int nb =0;
				int cb = 0;
				int a = 0;
				int b = 0;
				while(nb != 1000) {
					nb++;
					
					nj = (int) Math.round( 1 + (Math.random() * (main.arensansjeu.size() - 1 )));
					System.out.println("" + nj);
					if(nj == 1) {
						a++;
					}else if(nj == 2) {
						b++;
					}else {
						cb++;
					}
				}
				player.sendMessage("§a§lLe nombre de 1 : " + a);
				player.sendMessage("§c§lLe nombre de 2 : " + b);
				player.sendMessage("§6§l" + cb);**/


			}if(string.equalsIgnoreCase("setarene")) {
				main.inv.onAddArene(player);
			}if(string.equalsIgnoreCase("setsumo")) {
				main.inv.onAddSumo(player);
			}if(string.equalsIgnoreCase("setparcour")) {
				main.inv.onAddParcour(player);
			}if(string.equalsIgnoreCase("setffa")) {
				main.inv.onAddFfa(player);
			}if(string.equalsIgnoreCase("getarene")) {
				main.getar = 1;
				player.openInventory(main.inv.onArene(1, player));
			}if(string.equalsIgnoreCase("sword")) {				

				main.onGive(player);
				
				player.updateInventory();
				
				player.setHealth(20);
				player.setFoodLevel(20);		

			}if(string.equalsIgnoreCase("ping")) {
				EntityPlayer msplayer = ((CraftPlayer)player).getHandle();
				player.sendMessage("§6Vous avez : §f" + msplayer.ping + " §6ms");
			}if(string.equalsIgnoreCase("update")) {
				if(args.length == 0) {
					main.getConnect().updateElo(player.getName());
					player.sendMessage("§6Votre profil fut update !");
				}else if(player.hasPermission("practice.mod.use")) {
						main.getConnect().updateElo(args[0]);
						player.sendMessage("§6Le profil de " + args[0] + " fut update !");
				}
			}if(string.equalsIgnoreCase("elo")) {
				if(player.hasPermission("practice.elo.use")) {
					if(args.length == 4) {
						if(args[0].equalsIgnoreCase("add")) {
							main.getConnect().addElo(args[1], args[3], Integer.parseInt(args[2]));
							main.getConnect().updateElo(args[1]);
							player.sendMessage("§6§lL'élo a bien été modifier !");
						}else if(args[0].equalsIgnoreCase("remove")) {
							main.getConnect().removeElo(args[1], args[3], Integer.parseInt(args[2]));
							main.getConnect().updateElo(args[1]);
							player.sendMessage("§6§lL'élo a bien été modifier !");
						}else if(args[0].equalsIgnoreCase("set")) {
							main.getConnect().setElo(args[1], args[3], Integer.parseInt(args[2]));
							main.getConnect().updateElo(args[1]);
							player.sendMessage("§6§lL'élo a bien été modifier !");
						}else {
							player.sendMessage("§cLa commande est /elo add/remove/set <player> <elo> <idkit>");
						}
					}else {
						player.sendMessage("§cLa commande est /elo add/remove/set <player> <elo> <idkit>");
					}
				}else {
					player.sendMessage("§cVous n'avez pas acces a cette commande !");
					return false;
				}
			}if(string.equalsIgnoreCase("stat")) {
				if(args.length == 1) {
					player.openInventory(main.getInstance().mess.getinv.get(args[0]));
				}
			}if(string.equalsIgnoreCase("statdef")) {
				if(args.length == 1) {
					player.openInventory(main.getInstance().mess.getinv2.get(args[0]));
				}
			}

		}if(string.equalsIgnoreCase("debug")) {
			if(sender.hasPermission("practice.debug.use")) {
				File configurationFile = new File(main.getDataFolder(), "config.yml");
				int kt = main.getConfig().getInt("kitnumber");
				kt--;
				try {
		            try {
						main.getConfig().load(configurationFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					main.getConfig().set("kitnumber" , kt);
					sender.sendMessage("§l§cL'action a été effectuer avec succes. Je vous laisse tester de /kiteditor et si le problème persiste je vous invite a mp jeje_oh");
		            try {
						main.getConfig().save(configurationFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }catch (InvalidConfigurationException exe) {
		            exe.printStackTrace();
		            sender.sendMessage("§l§cIl y a eu un problème, je vous invite a mp jeje_oh.");
		        }
				
			}else sender.sendMessage("Vous n'avez pas les permission requise");
		}
		
		return false;
	}

}
