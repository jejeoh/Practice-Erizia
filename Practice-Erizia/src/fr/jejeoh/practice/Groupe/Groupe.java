package fr.jejeoh.practice.Groupe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Groupe {
	
	private Base main;
	
	public Groupe(Base main) {
		this.main = main;
		
	    main.getCommand("groupe").setExecutor(new CommandeGroupe(main, this ));
	    main.getCommand("g").setExecutor(new CommandeGroupe(main, this));

	    int nb = 0;
	    while(main.aren.getInt("ffa.number") != nb) {
	    	nb++;
	    	if(main.aren.getBoolean("ffa.all." + nb + ".act")) {
	    		st.add(nb+ "");
	    	}
	    }
		
	}
	

	
	
	public List<List<Player>> groupe = new ArrayList<>();
	public List<String> st = new ArrayList<>();

	public Map<Player, List<Player>> getGrp = new HashMap<>();
	public Map<List<Player>, Player> chef = new HashMap<>();
	public Map<Player, List<Player>> ffa = new HashMap<>();
	public Map<Player, List<Player>> getffa = new HashMap<>();
	public Map<Player, String> getid = new HashMap<>();
	public Map<Player, List<Block>> bl = new HashMap<>();

	
	public void onJoin(Player inv, Player rec) {
		if(getGrp.containsKey(inv)) {
			List<Player> li = getGrp.get(inv);
			li.add(rec);
			getGrp.put(rec, li);
			rec.sendMessage("§a§lVous avez rejoin le groupe de " + inv.getName());
			inv.sendMessage("§a§l" + rec.getName() + " a rejoin le groupe");
			if((!main.online.contains(inv)) && (!main.ranonline.contains(inv))) {
				onGive(inv);
			}
			if((!main.online.contains(rec)) && (!main.ranonline.contains(rec))) {
				onSous(rec);
			}
			return;
		}else {
			List<Player> li = new ArrayList<>();
			li.add(inv);
			li.add(rec);
			chef.put(li, inv);
			getGrp.put(inv, li);
			getGrp.put(rec, li);
			rec.sendMessage("§a§lVous avez rejoin le groupe de " + inv.getName());
			inv.sendMessage("§a§l" + rec.getName() + " a rejoin le groupe");
			if((!main.online.contains(inv)) && (!main.ranonline.contains(inv))) {
				onGive(inv);
			}
			if((!main.online.contains(rec)) && (!main.ranonline.contains(rec))) {
				onSous(rec);
			}
			return;
		}
	}
	public void onInvite(Player inv, Player rec) {
		TextComponent rej = new TextComponent("§6§lRejoindre le groupe de " + inv.getName());
		rej.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAccepter l'invitation").create()));
		rej.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g o2f8jn158 " + inv.getName()));
		
		rec.spigot().sendMessage(rej);
		inv.sendMessage("§a§lL'invitation a bien été envoyer à " + rec.getName());
	}
	public void onGet(Player player) {
		player.sendMessage("§6§lLe groupe est : ");
		List<Player> li = getGrp.get(player);
		for(Player play : li) {
			if(chef.containsValue(play)) {
				player.sendMessage("     §6§l-" + play.getName() + " ( chef )");
			}else {
				player.sendMessage("§6     -" + play.getName());
			}
		}
		
	}
	public void onLeave(Player player) {
		List<Player> li = getGrp.get(player);
		if(li.size() == 1) {
			li.remove(player);
			groupe.remove(li);
			getGrp.remove(player);
			chef.remove(li);
			player.sendMessage("§6§lTu as supprimé le groupe !");
			if((!main.online.contains(player)) && (!main.ranonline.contains(player))) {
				main.onGive(player);
			}
			return;
		}
		if(chef.containsValue(player)) {
			
			List<Player> lis = new ArrayList<>();
			
			for(Player play : li) {
				lis.add(play);
			}
			for(Player play : lis) {
				li.remove(play);
				getGrp.remove(play);
				play.sendMessage("§6§lLe groupe a été supprimé");
				if((!main.online.contains(play)) && (!main.ranonline.contains(play))) {
						main.onGive(play);
				}			}
			chef.remove(li);
			
			return;
		}

		Player play = chef.get(li);
		li.remove(player);
		getGrp.remove(player);
		if((!main.online.contains(play)) && (!main.ranonline.contains(play))) {
			main.onGive(play);
		}
		if((!main.online.contains(player)) && (!main.ranonline.contains(player))) {
			main.onGive(player);
		}
		player.sendMessage("§6§lTu as quitté le groupe");		
	}
	public void onKick(Player player) {
		List<Player> li = getGrp.get(player);
		Player inv = chef.get(li);
		li.remove(player);
		getGrp.remove(player);
		player.sendMessage("§6§lTu as été kick du groupe");
		if((!main.online.contains(inv)) && (!main.ranonline.contains(inv))) {
			main.onGive(inv);
		}
		if((!main.online.contains(player)) && (!main.ranonline.contains(player))) {
			main.onGive(player);
		}
	}
	public void onLead(Player player) {
		List<Player> li = getGrp.get(player);
		Player rec = chef.get(li);
		if((!main.online.contains(rec)) && (!main.ranonline.contains(rec))) {
			onSous(rec);
		}
		chef.remove(li);
		chef.put(li, player);
		player.sendMessage("§6§lTu es maintenant chef su groupe !");
		if((!main.online.contains(player)) && (!main.ranonline.contains(player))) {
			onGive(player);
		}
	}
	public void onGive(Player player) {
		player.getInventory().clear();
		
		ItemStack deux = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta ite = deux.getItemMeta();
		if(getGrp.get(player).size() != 2) {
			ite.setDisplayName("§c§l1vs1 - trop nombreux");
		}else {
			ite.setDisplayName("§6§l1vs1");
		}
		
		ItemStack ffa = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta met = ffa.getItemMeta();
		if(getGrp.get(player).size() < 2) {
			met.setDisplayName("§c§lffa - pas assez nombreux");
		}else if(!player.hasPermission("practice.ffa.use")) {
			met.setDisplayName("§c§lffa - pas assez grader");
		}else {
			met.setDisplayName("§c§lffa");
		}
		
		ItemStack kb = new ItemStack(Material.STICK, 1);
		ItemMeta me = kb.getItemMeta();
		if(getGrp.get(player).size() < 2) {
			me.setDisplayName("§c§lkb - pas assez nombreux");
		}else if(!player.hasPermission("practice.kb.use")) {
			me.setDisplayName("§c§lkb - pas assez grader");
		}else {
			me.setDisplayName("§b§lkb");
		}
		
		ItemStack quit = new ItemStack(Material.BARRIER, 1);
		ItemMeta qu = quit.getItemMeta();
		qu.setDisplayName("§c§lQuitter le groupe");
		
		
		deux.setItemMeta(ite);
		ffa.setItemMeta(met);
		kb.setItemMeta(me);
		quit.setItemMeta(qu);
		
		player.getInventory().setItem(0, deux);
		player.getInventory().setItem(1, ffa);
		player.getInventory().setItem(2, kb);
		player.getInventory().setItem(8, quit);

	}
	public void onSous(Player player) {
		player.getInventory().clear();
		
		ItemStack quit = new ItemStack(Material.BARRIER, 1);
		ItemMeta qu = quit.getItemMeta();
		qu.setDisplayName("§c§lQuitter le groupe");
		quit.setItemMeta(qu);

		player.getInventory().setItem(8, quit);
	}
	public Inventory onDuel(Player player) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);


		
		if(kite.getInt("kitnumber") <= 5) {
			inv = Bukkit.createInventory(player, 45, "§6§l1vs1");
		}else if(main.getConfig().getInt("kitnumber") >= 6) {
			inv = Bukkit.createInventory(player, 54, "§6§l1vs1");
		}
		int nb = 0;
		int numb = 0;
		ItemStack kit = null;
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if(kite.getBoolean("kit." + nb + ".act") == true) {
				
				
				kit = kite.getItemStack("kit." + nb + ".item");
				ItemMeta kit2 = kit.getItemMeta();
				String stre = kite.getString("kit." + nb + ".name");
				kit2.setDisplayName("§6§l" + stre);
				kit2.setLore(null);
				kit2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				if(kit2.hasEnchant(Enchantment.DURABILITY)) {
					kit2.removeEnchant(Enchantment.DURABILITY);
				}
				kit.setItemMeta(kit2);
				numb++;
				if( nb <= 3) {
					inv.setItem(11+numb, kit);
				}else if( nb <= 8){
					inv.setItem(16+numb, kit);
				}else if( nb <= 13){
					inv.setItem(20+numb, kit);
				}else{
					inv.setItem(25+numb, kit);
				}
				
			}
		}
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);

		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);

		if( nb <= 5) {

			
			inv.setItem(27, gpvert);
			inv.setItem(35, gpvert);

			inv.setItem(36, gpvert);
			inv.setItem(37, gpvert);
			inv.setItem(38, gpvert);

			inv.setItem(42, gpvert);
			inv.setItem(43, gpvert);
			inv.setItem(44, gpvert);
		}else {
			inv.setItem(36, gpvert);
			inv.setItem(44, gpvert);

			inv.setItem(45, gpvert);
			inv.setItem(46, gpvert);
			inv.setItem(47, gpvert);

			inv.setItem(51, gpvert);
			inv.setItem(52, gpvert);
			inv.setItem(53, gpvert);
		}


		
		return inv;
	}
	
	public void ouDuel(Player player, String id) {
		
		List<Player> li = getGrp.get(player);
		
		Boolean act = false;
		
		for(Player play : li) {
		
			
			if(main.online.contains(play) || main.ranonline.contains(play) || (main.att.contains(play))) {
				act = true;
				player.sendMessage("§c§lIl faut que tout les joueurs ne soient pas en partie.");
				break;
			}		
			
			if(play == player);
			else {
				main.duel.put(player, play);
				break;
			}
		}
		if(act)return;
		
		main.duelkit.put(player, id);
		
		main.duele.onStart(player);
	}
	
	
	
	
	public void onFfa(Player player, String id) {
		
		List<Player> li = getGrp.get(player);

		
		Boolean act = false;
		
		for(Player play : li) {
			if(main.online.contains(play) || main.ranonline.contains(play) || (main.att.contains(play))) {
				act = true;
				player.sendMessage("§c§lIl faut que tout les joueurs ne soient pas en partie.");
				break;
			}}
		
		if(act)return;
		
		List<Player> lis = new ArrayList<>();
		List<Player> full = new ArrayList<>();
		List<Block> blo = new ArrayList<>();
		
		int nb = (int) Math.round( 1 + (Math.random() * (st.size() - 1 )));
		
		int nomb = 0;
		String i = "";

		for(String str : st) {
			nomb++;
			if(nb == nomb) {
				i = str;
				break;
			}
		}
		
		st.remove(i);
		
		for(Player play : li) {
			Give(play, id);

			play.teleport(onId(i));
			main.getInstance().title.sendActionBar(play, "§6Arene : " + main.aren.getString("ffa.all." + i + ".name"));

			getid.put(play, i);
			lis.add(play);
			full.add(play);
			ffa.put(play, lis);
			getffa.put(play, full);
			bl.put(play, blo);

			main.online.add(play);
		}
		
		
		
		
		
		
	}
	
	
	public void Give(Player player, String kit) {
		
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
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

	}
	public void onKill(Player player) {
		List<Player> li = ffa.get(player);
				
		li.remove(player);
		
		player.setGameMode(GameMode.SPECTATOR);
		
		player.setFireTicks(0);
		player.setHealth(20);
		if(li.size() == 1) {
			End(player);
			return;
		}
	}
	
	
	
	
	
	public void End(Player player) {
		
		List<Player> lis = getffa.get(player);
		List<Player> li = ffa.get(player);
		List<Block> blo = bl.get(player);
		st.add(getid.get(player));

		
		for(Player pl : li) {			
			pl.setHealth(20);
			main.title.sendTitle(pl, 10, 20, 10, "§6§lTu as gagner");
			
			break;
		}
		for(Block bloc : blo) {
			bloc.setType(Material.AIR);
		}
		
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));

		for(Player play : lis) {
			play.setFoodLevel(20);		
			play.setFireTicks(0);		

			
			getid.remove(play);
			ffa.remove(play);
			play.teleport(loc);
			play.setGameMode(GameMode.SURVIVAL);
			main.online.remove(play);
			main.onGive(play);
			getffa.remove(play);
			bl.remove(play);
		}
	}
	
	
	
	
	
	
	
	
	public Inventory onFfa(Player player) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);


		
		if(kite.getInt("kitnumber") <= 5) {
			inv = Bukkit.createInventory(player, 45, "§6§lffa");
		}else if(main.getConfig().getInt("kitnumber") >= 6) {
			inv = Bukkit.createInventory(player, 54, "§6§lffa");
		}
		int nb = 0;
		int numb = 0;
		ItemStack kit = null;
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if((kite.getBoolean("kit." + nb + ".act")) && (!kite.getBoolean("kit." + nb + ".prop.sumo")) && (!kite.getBoolean("kit." + nb + ".prop.parcour"))) {
				
				
				kit = kite.getItemStack("kit." + nb + ".item");
				ItemMeta kit2 = kit.getItemMeta();
				String stre = kite.getString("kit." + nb + ".name");
				kit2.setDisplayName("§6§l" + stre);
				kit2.setLore(null);
				kit2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				if(kit2.hasEnchant(Enchantment.DURABILITY)) {
					kit2.removeEnchant(Enchantment.DURABILITY);
				}
				kit.setItemMeta(kit2);
				numb++;
				if( numb <= 3) {
					inv.setItem(11+numb, kit);
				}else if( numb <= 8){
					inv.setItem(16+numb, kit);
				}else if( numb <= 13){
					inv.setItem(20+numb, kit);
				}else{
					inv.setItem(25+numb, kit);
				}
				
			}
		}
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);

		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);

		if( nb <= 5) {

			
			inv.setItem(27, gpvert);
			inv.setItem(35, gpvert);

			inv.setItem(36, gpvert);
			inv.setItem(37, gpvert);
			inv.setItem(38, gpvert);

			inv.setItem(42, gpvert);
			inv.setItem(43, gpvert);
			inv.setItem(44, gpvert);
		}else {
			inv.setItem(36, gpvert);
			inv.setItem(44, gpvert);

			inv.setItem(45, gpvert);
			inv.setItem(46, gpvert);
			inv.setItem(47, gpvert);

			inv.setItem(51, gpvert);
			inv.setItem(52, gpvert);
			inv.setItem(53, gpvert);
		}


		
		return inv;
	}
	
	public Location onId(String id) {
		
		
		int xdiff = main.aren.getInt("ffa.all." + id + ".point1.x");
		int zdiff = 14;
		int x2 = -350;
		int z2 = 941;
		int y = main.aren.getInt("ffa.all." + id + ".point1.y") + 1;
		
		int x = 0;
		int z = 0;

		if(main.aren.getInt("ffa.all." + id + ".point1.x") <= main.aren.getInt("ffa.all." + id + ".point2.x")) {
			xdiff = main.aren.getInt("ffa.all." + id + ".point2.x") - main.aren.getInt("ffa.all." + id + ".point1.x");
			x2 = main.aren.getInt("ffa.all." + id + ".point1.x");
		}else {
			xdiff = main.aren.getInt("ffa.all." + id + ".point1.x") - main.aren.getInt("ffa.all." + id + ".point2.x");
			x2 = main.aren.getInt("ffa.all." + id + ".point2.x");
		}
		
		if(main.aren.getInt("ffa.all." + id + ".point1.z") <= main.aren.getInt("ffa.all." + id + ".point2.z")) {
			zdiff = main.aren.getInt("ffa.all." + id + ".point2.z") - main.aren.getInt("ffa.all." + id + ".point1.z");
			z2 = main.aren.getInt("ffa.all." + id + ".point1.z");
		}else {
			zdiff = main.aren.getInt("ffa.all." + id + ".point1.z") - main.aren.getInt("ffa.all." + id + ".point2.z");
			z2 = main.aren.getInt("ffa.all." + id + ".point2.z");
		}
		
		
		x = (int) Math.round( 1 + (Math.random() * (xdiff - 1 )));
		z = (int) Math.round( 1 + (Math.random() * (zdiff - 1 )));

		x = x + x2;
		z = z + z2;
		
		
		Location loc = new Location(Bukkit.getWorld(main.aren.getString("ffa.all." + id + ".world")), x, y, z);
		
		return loc;
	}
	
	

}
