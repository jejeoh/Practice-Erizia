package fr.jejeoh.practice.Listenaire;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.Arene.AreneManager.Arene;

public class EventDeath implements Listener {
	
	private Base main;
	
	
	public EventDeath(Base main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		if(!(main.online.contains(player))) {
			e.setCancelled(true);
			return;
		}
		if(main.att.contains(player)) {
			e.setCancelled(true);
			return;
		}
		if(player.getHealth() > e.getFinalDamage()) return;
		e.setCancelled(true);
		Player play = main.game.get(player);
		player.setHealth(20);
		play.setHealth(20);
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
		main.game.remove(player);
		main.game.remove(play);
		player.teleport(loc);
		play.teleport(loc);
		player.sendMessage("§c§lVous avez perdu");
		play.sendMessage("§6§lTu as gagné");
		if(main.end.containsKey(player)) {
			Arene ar = main.end.get(player);
			main.end.remove(player);
			main.arensansjeu.add(ar);
			main.arenenjeu.remove(ar);
		}if(main.end.containsKey(play)) {
			Arene ar = main.end.get(play);
			main.end.remove(play);
			main.arensansjeu.add(ar);
			main.arenenjeu.remove(ar);
		}
		
		List<Block> lit = null;
		if(main.bc.containsKey(player)) {
			lit = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			lit = main.bc.get(p);
		}
		
		for(Block bl : lit) {
			bl.setType(Material.AIR);
		}
		
		main.online.remove(player);
		main.online.remove(play);
		
		player.getInventory().clear();
		play.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		play.getInventory().setHelmet(null);
		play.getInventory().setChestplate(null);
		play.getInventory().setLeggings(null);
		play.getInventory().setBoots(null);
		
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
		
				
		player.getInventory().setItem(0, unranked);
		player.getInventory().setItem(1, ranked);
		
		play.getInventory().setItem(0, unranked);
		play.getInventory().setItem(1, ranked);

		play.updateInventory();
		player.updateInventory();
		int id = 0;
		while(id != main.getConfig().getInt("kitnumber")) {
			id++;
			
			List<Player> li = main.kitingame.get("" + id);
			if(li.contains(player)) {
				li.remove(player);
				li.remove(play);
				break;
			}
			
		}
			
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(!(main.online.contains(player))) {
			return;
		}

		Player play = main.game.get(player);
		player.setHealth(20);
		play.setHealth(20);
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
		main.game.remove(player);
		main.game.remove(play);
		play.teleport(loc);
		play.sendMessage("§6§lTu as gagné");
		if(main.end.containsKey(player)) {
			Arene ar = main.end.get(player);
			main.end.remove(player);
			main.arensansjeu.add(ar);
			main.arenenjeu.remove(ar);
		}if(main.end.containsKey(play)) {
			Arene ar = main.end.get(play);
			main.end.remove(play);
			main.arensansjeu.add(ar);
			main.arenenjeu.remove(ar);
		}
		
		main.online.remove(player);
		main.online.remove(play);
		
		play.getInventory().clear();
		
		play.getInventory().setHelmet(null);
		play.getInventory().setChestplate(null);
		play.getInventory().setLeggings(null);
		play.getInventory().setBoots(null);
		
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
		
				
		play.getInventory().setItem(0, unranked);
		play.getInventory().setItem(1, ranked);

		play.updateInventory();
		int id = 0;
		while(id != main.getConfig().getInt("kitnumber")) {
			id++;
			
			List<Player> li = main.kitingame.get("" + id);
			if(li.contains(player)) {
				li.remove(player);
				li.remove(play);
				break;
			}
			
		}

	}
	
	
	
	@EventHandler
	public void onSet(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		Block b = e.getBlock();
		if(!(main.online.contains(player))) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.bc.containsKey(player)) {
			li = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			li = main.bc.get(p);
		}
		
		li.add(b);
	}
	
	@EventHandler
	public void onRemove(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block b = e.getBlock();
		if(!(main.online.contains(player))) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.bc.containsKey(player)) {
			li = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			li = main.bc.get(p);
		}
		if(li.contains(b)) {
			li.remove(b);
		}else {
			e.setCancelled(true);
			player.sendMessage("Vous ne pouvez déterriorez l'arene !");
		}

		
	}
	
}
