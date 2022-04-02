package fr.jejeoh.practice.Arene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.NumberTimer;

public class Properties {
	
	private Base main;
	
	public Map<Player, String> game = new HashMap<>();
	
	
	
	
	public Properties(Base main) {
		this.main = main;
		
		int nb = 0;
		while(nb != main.aren.getInt("sumo.number")) {
			nb++;
			if(main.aren.getBoolean("sumo.all." + nb +".act")) {
				Sumo arene = new Sumo("" + nb);
				main.sumosansjeu.add(arene);
			}
		}
		nb = 0;
		while(nb != main.aren.getInt("parcour.number")) {
			nb++;
			if(main.aren.getBoolean("parcour.all." + nb +".act")) {
				Parcour arene = new Parcour("" + nb);
				main.parcoursansjeu.add(arene);
			}
		}
		
	}
	
	
	
	public class Sumo{
		
		private String name = null;
		private Location player1 = null;
		private Location player2 = null;
		private int y = 0;
		private String id = null;
		


		public Sumo(String id) {
			float ft1 = 0;
			float ft2 = 0;
					name = main.aren.getString("sumo.all." + id +".name");
					ft1 =  main.aren.getInt("sumo.all." + id +".player1.yaw");
					ft2 = main.aren.getInt("sumo.all." + id +".player1.pitch");
					player1 = new Location(Bukkit.getWorld(main.aren.getString("sumo.all." + id +".world")), main.aren.getInt("sumo.all." + id +".player1.x"), main.aren.getInt("sumo.all." + id +".player1.y"), main.aren.getInt("sumo.all." + id +".player1.z"), ft1, ft2);
					ft1 =  main.aren.getInt("sumo.all." + id +".player2.yaw");
					ft2 = main.aren.getInt("sumo.all." + id +".player2.pitch");
					player2 = new Location(Bukkit.getWorld(main.aren.getString("sumo.all." + id +".world")), main.aren.getInt("sumo.all." + id +".player2.x"), main.aren.getInt("sumo.all." + id +".player2.y"), main.aren.getInt("sumo.all." + id +".player2.z"), ft1, ft2);

					y = main.aren.getInt("sumo.all." + id +".y");
					this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public Location getFirst() {
			return player1;
		}
		public Location getSecond() {
			return player2;
		}
		public int getY() {
			return y;
		}
		public String getId() {
			return id;
		}
		
	}
	
	public class Parcour{
		
		private String name = null;
		private Location player1 = null;
		private Location player2 = null;
		private int y = 0;
		private String id = null;
		private int xmax = 0;
		private int xmin = 0;
		private int zmax = 0;
		private int zmin = 0;


		public Parcour(String id) {
			float ft1 = 0;
			float ft2 = 0;
					name = main.aren.getString("parcour.all." + id +".name");
					ft1 =  main.aren.getInt("parcour.all." + id +".player1.yaw");
					ft2 = main.aren.getInt("parcour.all." + id +".player1.pitch");
					player1 = new Location(Bukkit.getWorld(main.aren.getString("parcour.all." + id +".world")), main.aren.getInt("parcour.all." + id +".player1.x"), main.aren.getInt("parcour.all." + id +".player1.y"), main.aren.getInt("parcour.all." + id +".player1.z"), ft1, ft2);
					ft1 =  main.aren.getInt("parcour.all." + id +".player2.yaw");
					ft2 = main.aren.getInt("parcour.all." + id +".player2.pitch");
					player2 = new Location(Bukkit.getWorld(main.aren.getString("parcour.all." + id +".world")), main.aren.getInt("parcour.all." + id +".player2.x"), main.aren.getInt("parcour.all." + id +".player2.y"), main.aren.getInt("parcour.all." + id +".player2.z"), ft1, ft2);

					y = main.aren.getInt("parcour.all." + id +".end.y");
					this.id = id;

					if(main.aren.getInt("parcour.all." + id +".end.point1.x") >= main.aren.getInt("parcour.all." + id +".end.point2.x")) {
						xmax = main.aren.getInt("parcour.all." + id +".end.point1.x");
						xmin = main.aren.getInt("parcour.all." + id +".end.point2.x");
					}else {
						xmax = main.aren.getInt("parcour.all." + id +".end.point2.x");
						xmin = main.aren.getInt("parcour.all." + id +".end.point1.x");
					}
					if(main.aren.getInt("parcour.all." + id +".end.point1.z") >= main.aren.getInt("parcour.all." + id +".end.point2.z")) {
						zmax = main.aren.getInt("parcour.all." + id +".end.point1.z");
						zmin = main.aren.getInt("parcour.all." + id +".end.point2.z");
					}else {
						zmax = main.aren.getInt("parcour.all." + id +".end.point2.z");
						zmin = main.aren.getInt("parcour.all." + id +".end.point1.z");
					}
		
		}
		
		public String getName() {
			return name;
		}
		
		public Location getFirst() {
			return player1;
		}
		public Location getSecond() {
			return player2;
		}
		public int getY() {
			return y;
		}
		public String getId() {
			return id;
		}
		
		public Boolean onGood(Location loc) {
			Boolean bl = false;
			if((loc.getX() <= xmax) && (loc.getX() >= xmin) && (loc.getZ() >= zmin) && (loc.getZ() <= zmax) && (loc.getY() >= y)) {
				bl = true;
			}
			return bl;
		}
		
	}
	private int y = 0;

	private int xmax = 0;
	private int xmin = 0;
	private int zmax = 0;
	private int zmin = 0;
	
	public Boolean onGood(Location loc, String id) {
		
		
		y = main.aren.getInt("parcour.all." + id +".end.y");

		if(main.aren.getInt("parcour.all." + id +".end.point1.x") >= main.aren.getInt("parcour.all." + id +".end.point2.x")) {
			xmax = main.aren.getInt("parcour.all." + id +".end.point1.x");
			xmin = main.aren.getInt("parcour.all." + id +".end.point2.x");
		}else {
			xmax = main.aren.getInt("parcour.all." + id +".end.point2.x");
			xmin = main.aren.getInt("parcour.all." + id +".end.point1.x");
		}
		if(main.aren.getInt("parcour.all." + id +".end.point1.z") >= main.aren.getInt("parcour.all." + id +".end.point2.z")) {
			zmax = main.aren.getInt("parcour.all." + id +".end.point1.z");
			zmin = main.aren.getInt("parcour.all." + id +".end.point2.z");
		}else {
			zmax = main.aren.getInt("parcour.all." + id +".end.point2.z");
			zmin = main.aren.getInt("parcour.all." + id +".end.point1.z");
		}
		
		Boolean bl = false;
		if((loc.getX() <= xmax) && (loc.getX() >= xmin) && (loc.getZ() >= zmin) && (loc.getZ() <= zmax) && (loc.getY() >= y)) {
			bl = true;
		}
		return bl;


	}
	
	public void CreatSumo(String id) {
		Sumo arene = new Sumo(id);
		main.sumosansjeu.add(arene);
		return;
	}

	

	public void onSumo(String kit, Player player, Player pl) {
						
		
		
		/**
		 * pl c'est égale a l'autre joueur
		 */
		
		

		
		
		
		if(pl.getName().equalsIgnoreCase(player.getName())) {
			player.sendMessage("§c§lErreur interne! Rééssayer");
			return;
		}
		
		
		/**
		 * On enleve le joueur a ses classes
		 */
		
		
		
		List<Sumo> ar = main.sumosansjeu;
		Sumo are = null;
		int rf = 0;
		int nj = (int) Math.round(Math.random() * ( main.sumosansjeu.size() - 1 ));
		for(Sumo arene : ar) {
			if(rf == nj) {
				are = arene;
				break;
			}
			rf++;
		}
		
		
		
		if(are == null) {
			player.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			pl.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			
			if(main.ranonline.contains(player)) {
				main.ranonline.remove(player);
				main.ranonline.remove(pl);
			}else {
				main.online.remove(player);
				main.online.remove(pl);

			}
			
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
			
			pl.getInventory().setItem(0, unranked);
			pl.getInventory().setItem(1, ranked);
			pl.getInventory().setItem(4, main.ed.Item());
			return;
		}
		
		
		
		/**
		 * On séléctionne une arene
		 */
		
		
		ar.remove(are);
		main.sumoenjeu.add(are);
		main.sumoar.put(player, are.getId());
		main.sumoar.put(pl, are.getId());

		/**
		 * On s'occupe de l'arene
		 */
		

		
		main.sumoget.put(player,""+ are.getY());
		main.sumoget.put(pl,""+ are.getY());
		
		
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
		
		main.sumoend.put(pl, are);

		main.sumoonline.add(player);
		main.sumoonline.add(pl);
		
		main.att.add(player);
		main.att.add(pl);

		player.getInventory().clear();
		pl.getInventory().clear();
		
		int nb = 0;
		int nombre = 1;
		ItemStack it = null;
		FileConfiguration internDB  = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

		if(internDB.getBoolean(player.getUniqueId() + "." + kit + ".act")) {
			while(nb != 36) {
				YamlConfiguration.loadConfiguration(main.getFile("internDB"));
				it = internDB.getItemStack(player.getUniqueId() + "." +  kit + ".inventory." + nombre);
				 player.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.helmet");
			player.getInventory().setHelmet(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.chestplate");
			player.getInventory().setChestplate(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.leggings");
			player.getInventory().setLeggings(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.boots");
			player.getInventory().setBoots(it);

		}else {
			while(nb != 36) {
				it = kite.getItemStack("kit." + kit + ".inventory." + nombre);
				 player.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = kite.getItemStack("kit." + kit + ".inventory.helmet");
			player.getInventory().setHelmet(it);
			 
			it = kite.getItemStack("kit." + kit + ".inventory.chestplate");
			player.getInventory().setChestplate(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.leggings");
			player.getInventory().setLeggings(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.boots");
			player.getInventory().setBoots(it);
		}
		
		
		
		if(internDB.getBoolean(pl.getUniqueId() + "." + kit + ".act")) {
			
			nb = 0;
			nombre = 1;

			while(nb != 36) {
				it = internDB.getItemStack(pl.getUniqueId() +  "." +  kit + ".inventory." + nombre);
				 pl.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.helmet");
			pl.getInventory().setHelmet(it);
			 
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.chestplate");
			pl.getInventory().setChestplate(it);
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.leggings");
			pl.getInventory().setLeggings(it);
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.boots");
			pl.getInventory().setBoots(it);
		}else {
			
			nb = 0;
			nombre = 1;

			while(nb != 36) {
				it = kite.getItemStack("kit." + kit + ".inventory." + nombre);
				 pl.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = kite.getItemStack("kit." + kit + ".inventory.helmet");
			pl.getInventory().setHelmet(it);
			 
			it = kite.getItemStack("kit." + kit + ".inventory.chestplate");
			pl.getInventory().setChestplate(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.leggings");
			pl.getInventory().setLeggings(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.boots");
			pl.getInventory().setBoots(it);

		}
		
		
		main.getInstance().title.sendActionBar(player, "§6Arene : " + are.getName());
		main.getInstance().title.sendActionBar(pl, "§6Arene : " + are.getName());
		
		NumberTimer nt = new NumberTimer(main, 4);
		main.timerstart.add(nt);
		main.timerst.put(nt, player);

		return;
	}
	
	public void CreatParcour(String id) {
		Parcour arene = new Parcour(id);
		main.parcoursansjeu.add(arene);
		return;
	}

	

	public void onParcour(String kit, Player player, Player pl) {
						
		
		
		/**
		 * pl c'est égale a l'autre joueur
		 */
		
		

		
		
		
		if(pl.getName().equalsIgnoreCase(player.getName())) {
			player.sendMessage("§c§lErreur interne! Rééssayer");
			return;
		}
		
		
		/**
		 * On enleve le joueur a ses classes
		 */
		
		
		
		List<Parcour> ar = main.parcoursansjeu;
		Parcour are = null;
		int rf = 0;
		int nj = (int) Math.round(Math.random() * ( main.parcoursansjeu.size() - 1 ));
		for(Parcour arene : ar) {
			if(rf == nj) {
				are = arene;
				break;
			}
			rf++;
		}
		
		
		
		if(are == null) {
			player.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			pl.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			
			if(main.ranonline.contains(player)) {
				main.ranonline.remove(player);
				main.ranonline.remove(pl);
			}else {
				main.online.remove(player);
				main.online.remove(pl);

			}
			
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
			
			pl.getInventory().setItem(0, unranked);
			pl.getInventory().setItem(1, ranked);
			pl.getInventory().setItem(4, main.ed.Item());
			return;
		}
		
		
		
		/**
		 * On séléctionne une arene
		 */
		
		
		ar.remove(are);
		main.parcourenjeu.add(are);
		main.parcourar.put(player, are.getId());
		main.parcourar.put(pl, are.getId());

		/**
		 * On s'occupe de l'arene
		 */
		

		
		main.parcourget.put(player,""+ are.getY());
		main.parcourget.put(pl,""+ are.getY());
		
		
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
		
		main.parcourend.put(pl, are);

		main.parcouronline.add(player);
		main.parcouronline.add(pl);
		

		player.getInventory().clear();
		pl.getInventory().clear();
		
		int nb = 0;
		int nombre = 1;
		ItemStack it = null;
		FileConfiguration internDB  = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

		if(internDB.getBoolean(player.getUniqueId() + "." + kit + ".act")) {
			while(nb != 36) {
				YamlConfiguration.loadConfiguration(main.getFile("internDB"));
				it = internDB.getItemStack(player.getUniqueId() + "." +  kit + ".inventory." + nombre);
				 player.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.helmet");
			player.getInventory().setHelmet(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.chestplate");
			player.getInventory().setChestplate(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.leggings");
			player.getInventory().setLeggings(it);
			YamlConfiguration.loadConfiguration(main.getFile("internDB"));
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.boots");
			player.getInventory().setBoots(it);

		}else {
			while(nb != 36) {
				it = kite.getItemStack("kit." + kit + ".inventory." + nombre);
				 player.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = kite.getItemStack("kit." + kit + ".inventory.helmet");
			player.getInventory().setHelmet(it);
			 
			it = kite.getItemStack("kit." + kit + ".inventory.chestplate");
			player.getInventory().setChestplate(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.leggings");
			player.getInventory().setLeggings(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.boots");
			player.getInventory().setBoots(it);
		}
		
		
		
		if(internDB.getBoolean(pl.getUniqueId() + "." + kit + ".act")) {
			
			nb = 0;
			nombre = 1;

			while(nb != 36) {
				it = internDB.getItemStack(pl.getUniqueId() +  "." +  kit + ".inventory." + nombre);
				 pl.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.helmet");
			pl.getInventory().setHelmet(it);
			 
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.chestplate");
			pl.getInventory().setChestplate(it);
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.leggings");
			pl.getInventory().setLeggings(it);
			
			it = internDB.getItemStack(pl.getUniqueId() + "." + kit + ".inventory.boots");
			pl.getInventory().setBoots(it);
		}else {
			
			nb = 0;
			nombre = 1;

			while(nb != 36) {
				it = kite.getItemStack("kit." + kit + ".inventory." + nombre);
				 pl.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			
			it = kite.getItemStack("kit." + kit + ".inventory.helmet");
			pl.getInventory().setHelmet(it);
			 
			it = kite.getItemStack("kit." + kit + ".inventory.chestplate");
			pl.getInventory().setChestplate(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.leggings");
			pl.getInventory().setLeggings(it);
			
			it = kite.getItemStack("kit." + kit + ".inventory.boots");
			pl.getInventory().setBoots(it);

		}
		
		
		main.getInstance().title.sendActionBar(player, "§6Arene : " + are.getName());
		main.getInstance().title.sendActionBar(pl, "§6Arene : " + are.getName());
		
		main.getInstance().title.sendTitle(player, 10, 20, 10, "§6Go");
		main.getInstance().title.sendTitle(pl, 10, 20, 10, "§6Go");


		return;
	}
	
}
