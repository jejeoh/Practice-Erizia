package fr.jejeoh.practice.Arene;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.jejeoh.practice.Base;

public class AreneManager {


private Base main;

public AreneManager (Base main) {
	this.main = main;
	
	int number = main.getConfig().getInt("arene.number");
	int nb = 0;
	nb = 0;
	while(nb != number) {
		nb++;
		if(main.getConfig().getBoolean("arene.all." + nb +".act")) {
			Location player1 = new Location(Bukkit.getWorld(main.getConfig().getString("arene.all." + nb +".world")), main.getConfig().getInt("arene.all." + nb +".player1.x"), main.getConfig().getInt("arene.all." + nb +".player1.y"), main.getConfig().getInt("arene.all." + nb +".player1.z"));
			Location player2 = new Location(Bukkit.getWorld(main.getConfig().getString("arene.all." + nb +".world")), main.getConfig().getInt("arene.all." + nb +".player2.x"), main.getConfig().getInt("arene.all." + nb +".player2.y"), main.getConfig().getInt("arene.all." + nb +".player2.z"));
			Arene arene = new Arene(main.getConfig().getString("arene.all." + nb +".name"), player1, player2);
			main.arensansjeu.add(arene);
		}
		
	}
	nb = 0;
	while(nb != main.getConfig().getInt("kitnumber")) {
		nb++;
		if(main.getConfig().getBoolean("kit." + nb + ".act") == true) {
			List<Player> pl = new ArrayList<>();
			main.kit.put("" + nb, pl);
			List<Player> plu = new ArrayList<>();
			main.kitingame.put("" + nb, plu);
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
	public class Int{
		
		
		private int it = 0;


		public Int(int it) {
			this.it = it;
		}
		
		public int getInt() {
			return it;
		}
		public void moinInt() {
			it--;
			return;
		}
	}
	
	public void AddArene(String id) {
		
		Location player1 = new Location(Bukkit.getWorld(main.getConfig().getString("arene.all." + id +".world")), main.getConfig().getInt("arene.all." + id +".player1.x"), main.getConfig().getInt("arene.all." + id +".player1.y"), main.getConfig().getInt("arene.all." + id +".player1.z"));
		Location player2 = new Location(Bukkit.getWorld(main.getConfig().getString("arene.all." + id +".world")), main.getConfig().getInt("arene.all." + id +".player2.x"), main.getConfig().getInt("arene.all." + id +".player2.y"), main.getConfig().getInt("arene.all." + id +".player2.z"));
		Arene arene = new Arene(main.getConfig().getString("arene.all." + id +".name"), player1, player2);
		main.arensansjeu.add(arene);
		
		return;
	}
public void onGet(String kit, Player player) {
	
	List<Player> kt = main.kit.get(kit);
	
	if(kt.contains(player)) {
		kt.remove(player);
		main.play.remove(player);
		player.sendMessage("§6§lVous avez quitté la liste d'attente du " + main.getConfig().getString("kit." + kit + ".name"));
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
		player.sendMessage("§6§lVous avez était ajouté à la liste d'attente du " + main.getConfig().getString("kit." + kit + ".name"));
	}else {
		List<Player> li = main.kitingame.get(kit);
		Player pl = null;
		for(Player play : kt) {
			pl = play;
		}
		main.play.remove(pl);

		kt.remove(pl);
		
		List<Arene> ar = main.arensansjeu;
		Arene are = null;
		for(Arene arene : ar) {
			are = arene;
			break;
		}
		if(are == null) {
			player.sendMessage("§c§lErreur : Pas assez d'arènes merci de contacter un Administrateur !");
			pl.sendMessage("§c§lErreur : Pas assez d'arènes merci de contacter un Administrateur !");
			kt.remove(player);
			kt.remove(pl);
			return;
		}
		pl.sendMessage("§6§lLa partie vas commencé");
		player.sendMessage("§6§lLa partie vas commencé");

		ar.remove(are);
		main.arenenjeu.add(are);
		
		li.add(pl);
		li.add(player);
		
		List<Block> lis = new ArrayList<>();

		main.bc.put(pl, lis);
		
		player.setHealth(20);
		pl.setHealth(20);
		
		pl.teleport(are.getFirst());
		player.teleport(are.getSecond());
		
		main.game.put(pl, player);
		main.game.put(player, pl);
		
		main.end.put(pl, are);
		
		main.online.add(player);
		main.online.add(pl);
		
		main.att.add(player);
		main.att.add(pl);

		player.getInventory().clear();
		pl.getInventory().clear();
		
		int nb = 0;
		int nombre = 1;
		ItemStack it = null;
		while(nb != 36) {
			it = main.getConfig().getItemStack("kit." + kit + ".inventory." + nombre);
			 player.getInventory().setItem(nb, it);
			 pl.getInventory().setItem(nb, it);
			nb++;
			nombre++;
		}
		
		it = main.getConfig().getItemStack("kit." + kit + ".inventory.helmet");
		player.getInventory().setHelmet(it);
		pl.getInventory().setHelmet(it);
		 
		it = main.getConfig().getItemStack("kit." + kit + ".inventory.chestplate");
		player.getInventory().setChestplate(it);
		pl.getInventory().setChestplate(it);
		
		it = main.getConfig().getItemStack("kit." + kit + ".inventory.leggings");
		player.getInventory().setLeggings(it);
		pl.getInventory().setLeggings(it);
		
		it = main.getConfig().getItemStack("kit." + kit + ".inventory.boots");
		player.getInventory().setBoots(it);
		pl.getInventory().setBoots(it);
		
		Int inte = new Int(4);
		main.timerstart.add(inte);
		main.timerst.put(inte, player);

	}
	
	return;
}


}