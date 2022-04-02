package fr.jejeoh.practice.Commandes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.NumberTimer;
import fr.jejeoh.practice.Arene.AreneManager.Arene;

public class Duel {
	
	private Base main;
	
	public Duel(Base main) {
		this.main = main;
	}
	
	public void onStart(Player player) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

		Player pl = main.duel.get(player);
		String st = main.duelkit.get(player);
		
		
		
			List<Player> li = main.kitingame.get(st);
			


			main.duel.remove(player);
			main.duelkit.remove(player);
			

			
			if(pl.getName().equalsIgnoreCase(player.getName())) {
				player.sendMessage("§c§lErreur interne! Rééssayer");
				return;
			}
			
			main.online.add(player);
			main.online.add(pl);
			
			
			li.add(player);
			li.add(pl);
			
			if(kite.getBoolean("kit." + st + ".prop.sumo")) {
				main.arene.pro.onSumo(st, player, pl);
				return;
			}
			if(kite.getBoolean("kit." + st + ".prop.parcour")) {
				main.arene.pro.onParcour(st, player, pl);
				return;
			}
			/**
			 * On enleve le joueur a ses classes
			 */
			
			
			
			List<Arene> ar = main.arensansjeu;
			Arene are = null;
			int rf = 0;
			int nj = (int) Math.round( 1 + (Math.random() * (main.arensansjeu.size() - 1 )));
			for(Arene arene : ar) {
				rf++;
				if(rf == nj) {
					are = arene;
					break;
				}
			}
			
			
			
			if(are == null) {
				player.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
				pl.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");

				main.online.remove(player);
				main.online.remove(pl);
				
				li.remove(player);
				li.remove(pl);
				
				ItemStack unranked = new ItemStack(Material.IRON_SWORD);
				ItemMeta un = unranked.getItemMeta();
				un.setDisplayName("§e§lUnranked");
		        un.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				unranked.setItemMeta(un);
				
				ItemStack ranked = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta ran = ranked.getItemMeta();
				ran.setDisplayName("§c§lRanked");
		        ran.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				ranked.setItemMeta(ran);
				
				pl.getInventory().clear();
				pl.getInventory().setItem(0, unranked);
				pl.getInventory().setItem(1, ranked);
				pl.getInventory().setItem(4, main.ed.Item());
				return;
			}
			
			
			
			/**
			 * On séléctionne une arene
			 */
			
			
			ar.remove(are);
			main.arenenjeu.add(are);
			
			
			/**
			 * On s'occupe de l'arene
			 */
			
			li.add(pl);
			li.add(player);
			
			
			
			pl.sendMessage("§6§lLa partie va commencer");
			player.sendMessage("§6§lLa partie va commencer");

			
			
			
			
			List<Block> lis = new ArrayList<>();

			main.bc.put(pl, lis);
			
			player.setHealth(20);
			pl.setHealth(20);
			
			pl.teleport(are.getFirst());
			player.teleport(are.getSecond());
			
			main.game.put(pl, player);
			main.game.put(player, pl);
			
			main.end.put(pl, are);
						
			main.att.add(player);
			main.att.add(pl);

			player.getInventory().clear();
			pl.getInventory().clear();
			
			int nb = 0;
			int nombre = 1;
			ItemStack it = null;
			
			FileConfiguration internDB  = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));

			if(internDB.getBoolean(player.getUniqueId() + "." + st + ".act")) {
				while(nb != 36) {
					it = internDB.getItemStack(player.getUniqueId() + "." +  st + ".inventory." + nombre);
					 player.getInventory().setItem(nb, it);
					nb++;
					nombre++;
				}
				it = internDB.getItemStack(player.getUniqueId() + "." + st + ".inventory.helmet");
				player.getInventory().setHelmet(it);
				it = internDB.getItemStack(player.getUniqueId() + "." + st + ".inventory.chestplate");
				player.getInventory().setChestplate(it);
				it = internDB.getItemStack(player.getUniqueId() + "." + st + ".inventory.leggings");
				player.getInventory().setLeggings(it);
				it = internDB.getItemStack(player.getUniqueId() + "." + st + ".inventory.boots");
				player.getInventory().setBoots(it);

			}else {
				while(nb != 36) {
					it = kite.getItemStack("kit." + st + ".inventory." + nombre);
					 player.getInventory().setItem(nb, it);
					nb++;
					nombre++;
				}
				
				it = kite.getItemStack("kit." + st + ".inventory.helmet");
				player.getInventory().setHelmet(it);
				 
				it = kite.getItemStack("kit." + st + ".inventory.chestplate");
				player.getInventory().setChestplate(it);
				
				it = kite.getItemStack("kit." + st + ".inventory.leggings");
				player.getInventory().setLeggings(it);
				
				it = kite.getItemStack("kit." + st + ".inventory.boots");
				player.getInventory().setBoots(it);
			}
			
			
			
			if(internDB.getBoolean(pl.getUniqueId() + "." + st + ".act")) {
				
				nb = 0;
				nombre = 1;

				while(nb != 36) {
					it = internDB.getItemStack(pl.getUniqueId() +  "." +  st + ".inventory." + nombre);
					 pl.getInventory().setItem(nb, it);
					nb++;
					nombre++;
				}
				
				it = internDB.getItemStack(pl.getUniqueId() + "." + st + ".inventory.helmet");
				pl.getInventory().setHelmet(it);
				 
				it = internDB.getItemStack(pl.getUniqueId() + "." + st + ".inventory.chestplate");
				pl.getInventory().setChestplate(it);
				
				it = internDB.getItemStack(pl.getUniqueId() + "." + st + ".inventory.leggings");
				pl.getInventory().setLeggings(it);
				
				it = internDB.getItemStack(pl.getUniqueId() + "." + st + ".inventory.boots");
				pl.getInventory().setBoots(it);
			}else {
				
				nb = 0;
				nombre = 1;

				while(nb != 36) {
					it = kite.getItemStack("kit." + st + ".inventory." + nombre);
					 pl.getInventory().setItem(nb, it);
					nb++;
					nombre++;
				}
				
				it = kite.getItemStack("kit." + st + ".inventory.helmet");
				pl.getInventory().setHelmet(it);
				 
				it = kite.getItemStack("kit." + st + ".inventory.chestplate");
				pl.getInventory().setChestplate(it);
				
				it = kite.getItemStack("kit." + st + ".inventory.leggings");
				pl.getInventory().setLeggings(it);
				
				it = kite.getItemStack("kit." + st + ".inventory.boots");
				pl.getInventory().setBoots(it);

			}
			
			
			
			main.getInstance().title.sendActionBar(player, "§6Arene : " + are.getName());
			main.getInstance().title.sendActionBar(pl, "§6Arene : " + are.getName());			
			NumberTimer nt = new NumberTimer(main, 4);
			main.timerstart.add(nt);
			main.timerst.put(nt, player);

		
		
		return;
	}
	
	public void Particle(Player player, Color cl) {
	        Location loc = player.getLocation();
	        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	        FireworkMeta fwm = fw.getFireworkMeta();
	       
	        fwm.setPower(0);
	        fwm.addEffect(FireworkEffect.builder().withColor(cl).flicker(true).build());
	        
	       
	        fw.setFireworkMeta(fwm);
	        fw.detonate();
	       
	        for(int i = 0;i<1; i++){
	            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	            fw2.setFireworkMeta(fwm);
	        }
	    }	
	@SuppressWarnings("deprecation")
	public void Particles(Player player) {
		player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
	}
	
}
