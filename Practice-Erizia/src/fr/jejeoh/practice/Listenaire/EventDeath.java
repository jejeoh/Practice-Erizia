package fr.jejeoh.practice.Listenaire;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.NumberTimer;
import fr.jejeoh.practice.Arene.AreneManager.Arene;
import fr.jejeoh.practice.Arene.Properties.Parcour;
import fr.jejeoh.practice.Arene.Properties.Sumo;

public class EventDeath implements Listener {
	
	private Base main;
	
	
	public EventDeath(Base main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		
		if(main.att.contains(player)) {
			e.setCancelled(true);
			return;
		}
		
		if(main.sumoonline.contains(player)) {
			e.setDamage(0);
			return;
		}
		if(main.parcouronline.contains(player)) {
			e.setCancelled(true);
			return;
		}
		if((!(main.online.contains(player))) && (!(main.ranonline.contains(player)))) {
			e.setCancelled(true);
			return;
		}
		
		if(player.getHealth() > e.getFinalDamage()) return;
		if(main.online.contains(player)) {
			e.setCancelled(true);
			if(main.groupe.ffa.containsKey(player)) {
				main.groupe.onKill(player);
				return;
			}
			Player play = main.game.get(player);
			main.getInstance().mess.Message(play, player);
			player.setHealth(20);
			play.setHealth(20);
			player.setFoodLevel(20);		
			play.setFoodLevel(20);		
			player.setFireTicks(0);
			play.setFireTicks(0);
			main.getInstance().title.sendTitle(play, 10, 60, 10, "§6Victoire");
			main.getInstance().title.sendTitle(player, 10, 60, 10, "§cDéfaite");
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
			
			main.duele.Particle(play, Color.BLUE);
			main.duele.Particle(play, Color.GREEN);
			main.duele.Particle(play, Color.RED);
			
			main.duele.Particles(play);
			
			List<Block> lit = null;
			if(main.bc.containsKey(player)) {
				lit = main.bc.get(player);
			}else {
				lit = main.bc.get(play);
			}
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 5));
	
			
			main.online.remove(player);
			main.online.remove(play);
			
			main.att.add(player);
			main.att.add(play);
	
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
			
			int id = 0;
			
			FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
			
			while(id != kite.getInt("kitnumber")) {
				id++;
				if(kite.getBoolean("kit." + id + ".act")) {
					List<Player> li = main.kitingame.get("" + id);
					if(li.contains(player)) {
						li.remove(player);
						li.remove(play);
						break;
					}
				}}
			for(Block bl : lit) {
				bl.setType(Material.AIR);
			}
			if(main.bc.containsKey(player)) {
				main.bc.remove(player);
			}else {
				main.bc.remove(play);
			}
			NumberTimer nt = new NumberTimer(main, 4);
			main.timeren.add(nt);
			main.timerend.put(nt, player);

			
			
			
			
		}else if(main.ranonline.contains(player)) {
			e.setCancelled(true);
			Player play = main.game.get(player);
			main.getInstance().mess.Message(play, player);
			player.setHealth(20);
			play.setHealth(20);
			player.setFoodLevel(20);		
			play.setFoodLevel(20);		
			main.getInstance().title.sendTitle(play, 10, 60, 10, "§6Victoire");
			main.getInstance().title.sendTitle(player, 10, 60, 10, "§cDéfaite");
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
				lit = main.bc.get(play);
			}
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 5));
			

			
			main.ranonline.remove(player);
			main.ranonline.remove(play);
			
			main.att.add(player);
			main.att.add(play);
	
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
			
			int id = 0;
			FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
			while(id != kite.getInt("kitnumber")) {
				id++;
				if(kite.getBoolean("kit." + id + ".ranked")) {
					List<Player> li = main.rankitingame.get("" + id);
					if(li.contains(player)) {
						li.remove(player);
						li.remove(play);
						break;
				}

				}}
			main.duele.Particle(play, Color.BLUE);
			main.duele.Particle(play, Color.GREEN);
			main.duele.Particle(play, Color.RED);
			
			main.duele.Particles(player);
			
			int elo = main.ran.getElo(play, player, "" + id);
			main.getConnect().addElo(play.getName(), "" + id, elo);
			main.getConnect().removeElo(player.getName(), "" + id, elo);
			
			main.getConnect().updateElo(player.getName());
			main.getConnect().updateElo(play.getName());
			
			for(Block bl : lit) {
				bl.setType(Material.AIR);
			}
			if(main.bc.containsKey(player)) {
				main.bc.remove(player);
			}else {
				main.bc.remove(play);
			}
			NumberTimer nt = new NumberTimer(main, 4);
			main.timeren.add(nt);
			main.timerend.put(nt, player);

		}

		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(main.att.contains(player)) {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
			}
		}
		if(main.groupe.getGrp.containsKey(player)) {
			main.groupe.onLeave(player);
		}
		if((!(main.online.contains(player))) && (main.ranonline.contains(player))) {
			return;
		}
		if(main.online.contains(player)) {
			if(main.groupe.ffa.containsKey(player)) {
				main.groupe.onKill(player);
				return;
			}
			Player play = main.game.get(player);
			main.getInstance().mess.Message(play, player);
			play.setHealth(20);
			play.setFoodLevel(20);		
	
			Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
			main.game.remove(player);
			main.game.remove(play);
			play.teleport(loc);
			main.getInstance().title.sendTitle(play, 10, 60, 10, "§6Victoire");

			
			if(main.sumoonline.contains(player)) {
				
				
				Sumo ar = null;
				if(main.end.containsKey(player)) {
					ar = main.sumoend.get(player);
					main.sumoend.remove(player);
				}if(main.end.containsKey(play)) {
					ar = main.sumoend.get(play);
					main.sumoend.remove(play);
				}
				
				String kt = main.sumoar.get(player);
				
				main.sumoget.remove(player);
				main.sumoget.remove(play);

				main.sumoonline.remove(player);
				main.sumoonline.remove(play);

				main.arene.pro.CreatSumo(kt);
				main.sumoenjeu.remove(ar);
				main.sumoar.remove(player);
				main.sumoar.remove(play);
				
				
			}else {
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
			}
			
			if(main.att.contains(player)) {
				main.att.remove(player);
				main.att.remove(play);
			}
	
			main.online.remove(player);
			main.online.remove(play);
			
			main.onGive(play);

			int id = 0;
			FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
			while(id != kite.getInt("kitnumber")) {
				id++;
				
				List<Player> li = main.kitingame.get("" + id);
				if(li.contains(player)) {
					li.remove(player);
					li.remove(play);
					break;
				}
				
			}

		}else if(main.ranonline.contains(player)) {
			Player play = main.game.get(player);
			main.getInstance().mess.Message(play, player);
			play.setHealth(20);
			play.setFoodLevel(20);		
	
			if(main.att.contains(player)) {
				main.att.remove(player);
				main.att.remove(play);
			}
			
			Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
			main.game.remove(player);
			main.game.remove(play);
			play.teleport(loc);
			main.getInstance().title.sendTitle(play, 10, 60, 10, "§6Victoire");
			if(main.sumoonline.contains(player)) {
				
				
				Sumo ar = null;
				if(main.end.containsKey(player)) {
					ar = main.sumoend.get(player);
					main.sumoend.remove(player);
				}if(main.end.containsKey(play)) {
					ar = main.sumoend.get(play);
					main.sumoend.remove(play);
				}
				
				String kt = main.sumoar.get(player);
				
				main.sumoget.remove(player);
				main.sumoget.remove(play);

				main.sumoonline.remove(player);
				main.sumoonline.remove(play);

				main.arene.pro.CreatSumo(kt);
				main.sumoenjeu.remove(ar);
				main.sumoar.remove(player);
				main.sumoar.remove(play);
				
				
			}else {
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
			}
			

	
			main.ranonline.remove(player);
			main.ranonline.remove(play);
			
			
			
			main.onGive(play);

			int id = 0;
			FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
			while(id != kite.getInt("kitnumber")) {
				id++;
				
				List<Player> li = main.rankitingame.get("" + id);
				if(li.contains(player)) {
					li.remove(player);
					li.remove(play);
					break;
				}
				
				int elo = main.ran.getElo(play, player, "" + id);
				main.getConnect().addElo(play.getName(), "" + id, elo);
				main.getConnect().removeElo(player.getName(), "" + id, elo);
				
				main.getConnect().updateElo(player.getName());
				main.getConnect().updateElo(play.getName());


			}

		}

	}
	
	
	
	@EventHandler
	public void onSet(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		Block b = e.getBlock();
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if((!(main.online.contains(player))) && (!(main.ranonline.contains(player)))) {
			e.setCancelled(true);
			return;
		}
		if(main.att.contains(player)) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.groupe.ffa.containsKey(player)) {
			li = main.groupe.bl.get(player);
		}
		else if(main.bc.containsKey(player)) {
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
		if(player.getGameMode() == GameMode.CREATIVE) return;
		Block b = e.getBlock();
		if((!(main.online.contains(player))) && (!(main.ranonline.contains(player)))) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.groupe.ffa.containsKey(player)) {
			li = main.groupe.bl.get(player);
		}
		else if(main.bc.containsKey(player)) {
			li = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			li = main.bc.get(p);
		}
		if(li.contains(b)) {
			li.remove(b);
		}else {
			e.setCancelled(true);
		}

		
	}
	
	@EventHandler
	public void onSetBucket(PlayerBucketEmptyEvent e) {
		
		Block b = e.getBlockClicked().getRelative(e.getBlockFace());
		
		Player player = e.getPlayer();
		
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if((!(main.online.contains(player))) && (!(main.ranonline.contains(player)))) {
			e.setCancelled(true);
			return;
		}
		if(main.att.contains(player)) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.groupe.ffa.containsKey(player)) {
			li = main.groupe.bl.get(player);
		}
		else if(main.bc.containsKey(player)) {
			li = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			li = main.bc.get(p);
		}
		
		li.add(b);
	}
	@EventHandler
	public void onRemoveBucket(PlayerBucketFillEvent e) {
		Player player = e.getPlayer();
		Block b = e.getBlockClicked();
		if(player.getGameMode() == GameMode.CREATIVE) return;

		
		if((!(main.online.contains(player))) && (!(main.ranonline.contains(player)))) {
			e.setCancelled(true);
			return;
		}
		List<Block> li = null;
		if(main.groupe.ffa.containsKey(player)) {
			li = main.groupe.bl.get(player);
		}
		else if(main.bc.containsKey(player)) {
			li = main.bc.get(player);
		}else {
			Player p = main.game.get(player);
			li = main.bc.get(p);
		}
		if(li.contains(b)) li.remove(b);
		else e.setCancelled(true);
			
		

		
	}


	
	@EventHandler
	 public void entityAttack(EntityDamageByEntityEvent e)
	  {
	   if(!(e.getEntity() instanceof Player))return;
	    if(e.getDamager() instanceof Arrow) {
	    	Player player = (Player) e.getEntity();

	        Arrow arrow = (Arrow) e.getDamager();
	        if(arrow.getShooter() instanceof Player) {
	             Player pl = (Player) arrow.getShooter();
	             String st = "§c";
	             int he = (int) (player.getHealth() - e.getFinalDamage());
	             int lo = (int) Math.round(he*0.45);
	             int lop = 0;
	             if(lo<9) {
	            	 lop = 9-lo;
	             }
	             int nb = 0;
	             while(nb != lo) {
	            	 st = st + "♥";
	            	 nb++;
	             }
	             nb = 0;
	             st = st + "§7";
	             while(nb != lop) {
	            	 st = st + "♥";
	            	 nb++;
	             }
	     		main.getInstance().title.sendActionBar(pl, st);
	        }
	        e.getDamager().remove();
	     }
	}
	
	
	
	
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		//parcour
		
		if(main.parcouronline.contains(player)) {
			if(main.arene.pro.onGood(player.getLocation(), main.parcourar.get(player))) {
				Player play = main.game.get(player);
				Parcour ar = null;
				if(main.end.containsKey(player)) {
					ar = main.parcourend.get(player);
					main.parcourend.remove(player);
				}if(main.end.containsKey(play)) {
					ar = main.parcourend.get(play);
					main.parcourend.remove(play);
				}
				
				
				String kt = main.parcourar.get(player);
				
			
				main.duele.Particle(player, Color.BLUE);
				main.duele.Particle(player, Color.GREEN);
				main.duele.Particle(player, Color.RED);

				main.duele.Particles(player);

				main.getInstance().mess.Message(play, player);
				player.setHealth(20);
				play.setHealth(20);
				player.setFoodLevel(20);		
				play.setFoodLevel(20);		
				player.setFireTicks(0);
				play.setFireTicks(0);
				main.getInstance().title.sendTitle(player, 10, 60, 10, "§6Victoire");
				main.getInstance().title.sendTitle(play, 10, 60, 10, "§cDéfaite");
				
				List<Block> lit = null;
				if(main.bc.containsKey(player)) {
					lit = main.bc.get(player);
				}else {
					lit = main.bc.get(play);
				}
				
				main.parcourget.remove(player);
				main.parcourget.remove(play);
				
				play.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 5));
		
				
				main.parcouronline.remove(player);
				main.parcouronline.remove(play);
				
				main.att.add(player);
				main.att.add(play);
				
				main.arene.pro.CreatParcour(kt);
				main.parcourenjeu.remove(ar);
				main.parcourar.remove(player);
				main.parcourar.remove(play);

		
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
				
				int id = 0;
				FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

				
				if(main.ranonline.contains(player)) {
					
					while(id != kite.getInt("kitnumber")) {
						id++;
						if(kite.getBoolean("kit." + id + ".act")) {
							List<Player> li = main.rankitingame.get("" + id);
							if(li.contains(player)) {
								li.remove(player);
								li.remove(play);
								break;
							}
						}}
					
					int elo = main.ran.getElo(player, play, "" + id);
					main.getConnect().addElo(player.getName(), "" + id, elo);
					main.getConnect().removeElo(play.getName(), "" + id, elo);
					
					main.getConnect().updateElo(play.getName());
					main.getConnect().updateElo(player.getName());
					
					main.ranonline.remove(player);
					main.ranonline.remove(play);

				}else {
					
					while(id != kite.getInt("kitnumber")) {
						id++;
						if(kite.getBoolean("kit." + id + ".act")) {
							List<Player> li = main.kitingame.get("" + id);
							if(li.contains(player)) {
								li.remove(player);
								li.remove(play);
								break;
							}
						}}
					
					main.online.remove(player);
					main.online.remove(play);
				}
				
				
				for(Block bl : lit) {
					bl.setType(Material.AIR);
				}
				if(main.bc.containsKey(player)) {
					main.bc.remove(player);
				}else {
					main.bc.remove(play);
				}
				NumberTimer nt = new NumberTimer(main, 4);
				main.timeren.add(nt);
				main.timerend.put(nt, player);
			


			}
		}
		
		if(!(main.sumoonline.contains(player))) {
			return;
		}

		
		
		if(Math.round(player.getLocation().getBlockY()) > Integer.parseInt(main.sumoget.get(player))) {
			return;
		}else {
			Player play = main.game.get(player);
			Sumo ar = null;
			if(main.end.containsKey(player)) {
				ar = main.sumoend.get(player);
				main.sumoend.remove(player);
			}if(main.end.containsKey(play)) {
				ar = main.sumoend.get(play);
				main.sumoend.remove(play);
			}
			
			
			String kt = main.sumoar.get(player);
			
			if(main.att.contains(player)) {
				if(main.sumoend.containsKey(player)) {
					float ft1 = 0;
					float ft2 = 0;
					ft1 =  main.aren.getInt("sumo.all." + kt +".player1.yaw");
					ft2 = main.aren.getInt("sumo.all." + kt +".player1.pitch");
					player.teleport(new Location(Bukkit.getWorld(main.aren.getString("sumo.all." + kt +".world")), main.aren.getInt("sumo.all." + kt +".player1.x"), main.aren.getInt("sumo.all." + kt +".player1.y"), main.aren.getInt("sumo.all." + kt +".player1.z"), ft1, ft2));


				}else {
					float ft1 =  main.aren.getInt("sumo.all." + kt +".player2.yaw");
					float ft2 = main.aren.getInt("sumo.all." + kt +".player2.pitch");
					player.teleport( new Location(Bukkit.getWorld(main.aren.getString("sumo.all." + kt +".world")), main.aren.getInt("sumo.all." + kt +".player2.x"), main.aren.getInt("sumo.all." + kt +".player2.y"), main.aren.getInt("sumo.all." + kt +".player2.z"), ft1, ft2));
				}
				return;
			}
		
			main.duele.Particle(play, Color.BLUE);
			main.duele.Particle(play, Color.GREEN);
			main.duele.Particle(play, Color.RED);

			main.duele.Particles(player);

			main.getInstance().mess.Message(play, player);
			player.setHealth(20);
			play.setHealth(20);
			player.setFoodLevel(20);		
			play.setFoodLevel(20);		
			player.setFireTicks(0);
			play.setFireTicks(0);
			main.getInstance().title.sendTitle(play, 10, 60, 10, "§6Victoire");
			main.getInstance().title.sendTitle(player, 10, 60, 10, "§cDéfaite");
			
			List<Block> lit = null;
			if(main.bc.containsKey(player)) {
				lit = main.bc.get(player);
			}else {
				lit = main.bc.get(play);
			}
			
			main.sumoget.remove(player);
			main.sumoget.remove(play);
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 5));
	
			
			main.sumoonline.remove(player);
			main.sumoonline.remove(play);
			
			main.att.add(player);
			main.att.add(play);
			
			main.arene.pro.CreatSumo(kt);
			main.sumoenjeu.remove(ar);
			main.sumoar.remove(player);
			main.sumoar.remove(play);

	
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
			
			int id = 0;
			FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

			
			if(main.ranonline.contains(player)) {
				
				while(id != kite.getInt("kitnumber")) {
					id++;
					if(kite.getBoolean("kit." + id + ".act")) {
						List<Player> li = main.rankitingame.get("" + id);
						if(li.contains(player)) {
							li.remove(player);
							li.remove(play);
							break;
						}
					}}
				
				int elo = main.ran.getElo(play, player, "" + id);
				main.getConnect().addElo(play.getName(), "" + id, elo);
				main.getConnect().removeElo(player.getName(), "" + id, elo);
				
				main.getConnect().updateElo(player.getName());
				main.getConnect().updateElo(play.getName());
				
				main.ranonline.remove(player);
				main.ranonline.remove(play);

			}else {
				
				while(id != kite.getInt("kitnumber")) {
					id++;
					if(kite.getBoolean("kit." + id + ".act")) {
						List<Player> li = main.kitingame.get("" + id);
						if(li.contains(player)) {
							li.remove(player);
							li.remove(play);
							break;
						}
					}}
				
				main.online.remove(player);
				main.online.remove(play);
			}
			
			
			for(Block bl : lit) {
				bl.setType(Material.AIR);
			}
			if(main.bc.containsKey(player)) {
				main.bc.remove(player);
			}else {
				main.bc.remove(play);
			}
			NumberTimer nt = new NumberTimer(main, 4);
			main.timeren.add(nt);
			main.timerend.put(nt, player);
		}
		
	}
	
	public class Inte{
		
		
		private int it = 0;


		public Inte(int it) {
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

	
}
