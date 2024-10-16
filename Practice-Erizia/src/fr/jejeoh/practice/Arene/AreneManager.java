package fr.jejeoh.practice.Arene;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.NumberTimer;

public class AreneManager {

	public Properties pro;

private Base main;

public AreneManager (Base main) {
	this.main = main;
	
	pro = new Properties(main);
	
	int number = main.aren.getInt("arene.number");
	int nb = 0;
	float ft1 = 0;
	float ft2 = 0;
	nb = 0;
	while(nb != number) {
		nb++;
		if(main.aren.getBoolean("arene.all." + nb +".act")) {
			ft1 =  main.aren.getInt("arene.all." + nb +".player1.yaw");
			ft2 = main.aren.getInt("arene.all." + nb +".player1.pitch");
			Location player1 = new Location(Bukkit.getWorld(main.aren.getString("arene.all." + nb +".world")), main.aren.getInt("arene.all." + nb +".player1.x"), main.aren.getInt("arene.all." + nb +".player1.y"), main.aren.getInt("arene.all." + nb +".player1.z"), ft1, ft2);
			ft1 =  main.aren.getInt("arene.all." + nb +".player2.yaw");
			ft2 = main.aren.getInt("arene.all." + nb +".player2.pitch");
			Location player2 = new Location(Bukkit.getWorld(main.aren.getString("arene.all." + nb +".world")), main.aren.getInt("arene.all." + nb +".player2.x"), main.aren.getInt("arene.all." + nb +".player2.y"), main.aren.getInt("arene.all." + nb +".player2.z"), ft1, ft2);
			Arene arene = new Arene(main.aren.getString("arene.all." + nb +".name"), player1, player2);
			main.arensansjeu.add(arene);
		}
	}
	nb = 0;
	
	FileConfiguration kit  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

	while(nb != kit.getInt("kitnumber")) {
		nb++;
		if(kit.getBoolean("kit." + nb + ".act") == true) {
			List<Player> pl = new ArrayList<>();
			main.kit.put("" + nb, pl);
			List<Player> plu = new ArrayList<>();
			main.kitingame.put("" + nb, plu);
		}
		if(kit.getBoolean("kit." + nb + ".ranked") == true) {
			List<Player> pl = new ArrayList<>();
			main.rankit.put("" + nb, pl);
			List<Player> plu = new ArrayList<>();
			main.rankitingame.put("" + nb, plu);
		}
	}
}

	public class Arene{
		
		private String name = null;
		private Location player1 = null;
		private Location player2 = null;
		

		


		public Arene(String name, Location player1, Location player2) {
					this.name = name;
					this.player1 = player1;
					this.player2 = player2;
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
		
		
		
	}	
	public void AddArene(String id) {
		float ft1 = 0;
		float ft2 = 0;
		ft1 =  main.aren.getInt("arene.all." + id +".player1.yaw");
		ft2 = main.aren.getInt("arene.all." + id +".player1.pitch");
		Location player1 = new Location(Bukkit.getWorld(main.aren.getString("arene.all." + id +".world")), main.aren.getInt("arene.all." + id +".player1.x"), main.aren.getInt("arene.all." + id +".player1.y"), main.aren.getInt("arene.all." + id +".player1.z"), ft1, ft2);
		ft1 =  main.aren.getInt("arene.all." + id +".player2.yaw");
		ft2 = main.aren.getInt("arene.all." + id +".player2.pitch");
		Location player2 = new Location(Bukkit.getWorld(main.aren.getString("arene.all." + id +".world")), main.aren.getInt("arene.all." + id +".player2.x"), main.aren.getInt("arene.all." + id +".player2.y"), main.aren.getInt("arene.all." + id +".player2.z"), ft1, ft2);
		Arene arene = new Arene(main.aren.getString("arene.all." + id +".name"), player1, player2);
		main.arensansjeu.add(arene);
		
		return;
	}
	
	
	
	public void onGet(String kit, Player player) {
		
		
		
	FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

	List<Player> kt = main.kit.get(kit);
	
	if(kt.contains(player)) {
		kt.remove(player);
		main.play.remove(player);
		player.sendMessage("§6§lVous avez quitté la liste d'attente du " + kite.getString("kit." + kit + ".name"));
		return;
	}
	
	
	
	
	if(kt.size() == 0) {
		kt.add(player);
		if(main.play.containsKey(player)) {
			String ki = main.play.get(player);
			List<Player> li = main.kit.get(ki);
			li.remove(player);
			main.play.remove(player);
		}
		main.play.put(player, kit);
		player.sendMessage("§6§lVous avez été ajouté à la liste d'attente du " + kite.getString("kit." + kit + ".name"));
		ItemStack it = new ItemStack(Material.REDSTONE, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("§c§lQuitter la fil d'attente");
		im.addEnchant(Enchantment.DURABILITY, 1, true);
		it.setItemMeta(im);
		player.getInventory().clear();
		player.getInventory().setItem(8, it);
		return;
	}
	
	
	
	
	
	else {
		
		List<Player> li = main.kitingame.get(kit);
		Player pl = null;
		for(Player play : kt) {
			pl = play;
		}
		
		main.online.add(player);
		main.online.add(pl);
		
		main.play.remove(pl);
		kt.remove(pl);
		
		li.add(pl);
		li.add(player);
		
		if(kite.getBoolean("kit." + kit + ".prop.sumo")) {
			pro.onSumo(kit, player, pl);
			return;
		}
		
		if(kite.getBoolean("kit." + kit + ".prop.parcour")) {
			pro.onParcour(kit, player, pl);
			return;
		}
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
		
		
		
		List<Arene> ar = main.arensansjeu;
		Arene are = null;
		int rf = 0;
		int nj = (int) Math.round( 1 + (Math.random() * (main.arensansjeu.size() - 1 )));
		player.sendMessage("§6§l" + nj);
		pl.sendMessage("§6§l" + nj);
		for(Arene arene : ar) {
			rf++;
			player.sendMessage("§aLe nombre est "  + rf);
			pl.sendMessage("§aLe nombre est "  + rf);
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

		if(internDB.getBoolean(player.getUniqueId() + "." + kit + ".act")) {
			while(nb != 36) {
				YamlConfiguration.loadConfiguration(main.getFile("internDB"));
				it = internDB.getItemStack(player.getUniqueId() + "." +  kit + ".inventory." + nombre);
				 player.getInventory().setItem(nb, it);
				nb++;
				nombre++;
			}
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.helmet");
			player.getInventory().setHelmet(it);
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.chestplate");
			player.getInventory().setChestplate(it);
			it = internDB.getItemStack(player.getUniqueId() + "." + kit + ".inventory.leggings");
			player.getInventory().setLeggings(it);
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

	}
	
	return;
}

public void onRanGet(String kit, Player player) {
	
	List<Player> kt = main.rankit.get(kit);
	FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
	
	if(kt.contains(player)) {
		kt.remove(player);
		main.play.remove(player);
		player.sendMessage("§6§lVous avez quitté la liste d'attente du kit ranked " + kite.getString("kit." + kit + ".name"));
		return;
	}
	
	if(kt.size() == 0) {
		
		
		kt.add(player);
		
		
		if(main.ranplay.containsKey(player)) {
			String ki = main.ranplay.get(player);
			List<Player> li = main.rankit.get(ki);
			li.remove(player);
			main.ranplay.remove(player);
		}
		
		
		else if(main.play.containsKey(player)) {
			String ki = main.play.get(player);
			List<Player> li = main.kit.get(ki);
			li.remove(player);
			main.play.remove(player);
		}
		
		
		main.ranplay.put(player, kit);
		player.sendMessage("§6§lVous avez été ajouté à la liste d'attente du kit ranked " + kite.getString("kit." + kit + ".name"));
		
		ItemStack it = new ItemStack(Material.REDSTONE, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("§c§lQuitter la fil d'attente");
		im.addEnchant(Enchantment.DURABILITY, 1, true);
		it.setItemMeta(im);
		player.getInventory().clear();
		player.getInventory().setItem(8, it);
		return;
	}else {
		
		// Lorsque qu'il y a une autre personne dans la fil d'attente
		

		List<Player> li = main.rankitingame.get(kit);

		
		Player pl = null;
		for(Player play : kt) {
			pl = play;
			break;
		}
		
		main.ranonline.add(player);
		main.ranonline.add(pl);
		
		main.play.remove(pl);
		kt.remove(pl);
		
		li.add(pl);
		li.add(player);
		
		if(kite.getBoolean("kit." + kit + ".prop.sumo")) {
			pro.onSumo(kit, player, pl);
			return;
		}
		if(kite.getBoolean("kit." + kit + ".prop.parcour")) {
			pro.onParcour(kit, player, pl);
			return;
		}
		
		/**
		 * pl c'est égale a l'autre joueur
		 */
				
		if(pl.getName().equalsIgnoreCase(player.getName())) {
			player.sendMessage("§c§lErreur interne veuillez retenter !");
			return;
		}

		
		/**
		 * Le joueur a été enlevé des liste
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
		
		/**
		 * On choisie une arene
		 */
		
		if(are == null) {
			player.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			pl.sendMessage("§c§lErreur : Pas assez d'arènes impossible d'en trouver veuillez rééssayer !");
			
			main.ranonline.remove(player);
			main.ranonline.remove(pl);
			
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
		 * Si l'arène est nul on annule tout
		 */
		
		ar.remove(are);

		/**
		 * arene est supprimer de la liste des arene sans jeu
		 */
		
		main.arenenjeu.add(are);
		
		
		
		
		main.att.add(player);
		main.att.add(pl);
		main.end.put(pl, are);
		
		main.game.put(pl, player);
		main.game.put(player, pl);
		
		/**
		 * main.ranonline c'est pour que le joueur a le status de en ligne
		 * main.att c'est pour que le joueur ne puisse rien faire avant que la partie commence
		 * main.end c'est la map pour retrouver l'arene grace a un joueur
		 * main.game c'est la map qui dit quelle joueur est contre lequelle
		 */
		
		
		List<Block> lis = new ArrayList<>();
		main.bc.put(pl, lis);
		
			player.setHealth(20);
		pl.setHealth(20);
		
		pl.teleport(are.getFirst());
		player.teleport(are.getSecond());
		

		
		
		pl.sendMessage("§6§lLa partie va commencer");
		player.sendMessage("§6§lLa partie va commencer");



		player.getInventory().clear();
		pl.getInventory().clear();
		
		/**
		 * Clear le joueur
		 */
		
		int nb = 0;
		int nombre = 1;
		ItemStack it = null;
		FileConfiguration internDB  = YamlConfiguration.loadConfiguration(main.getFile("internDB"));

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
		/**
		 * Give des items
		 */
		
		NumberTimer nt = new NumberTimer(main, 4);
		main.timerstart.add(nt);
		main.timerst.put(nt, player);

		/**
		 * Start du timer
		 */
		
	}
	
	return;
}



}