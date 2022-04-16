package fr.jejeoh.practice.Listenaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.jejeoh.practice.Base;

public class EventGame implements Listener {

	
	private Base main;
	
	
	public EventGame(Base main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		main.getConnect().createAccount(player);
		
		main.getConnect().updateElo(player.getName());		
		
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		main.onGive(player);
		

		player.setHealth(20);
		player.setFoodLevel(20);		
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
		player.teleport(loc);
		
		

		int nb = main.online.size() + main.ranonline.size();
			
		
		final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
		final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");
		
		objetctive.setDisplayName("§f-- §6§lErizia§f --");
		objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);

		
		final Score bar2 = objetctive.getScore("§6§l―――――――――――――――――― ");
		final Score vide3 = objetctive.getScore("  ");
		final Score grade = objetctive.getScore("§6| Grade : ");
		final Score elo = objetctive.getScore("§6| Elo : §f" + main.getConnect().getElo(player.getName(), "0"));
		final Score vide2 = objetctive.getScore(" ");
		final Score vide4 = objetctive.getScore("     ");
		final Score joueur = objetctive.getScore("§6| §aConnectés : §f" + Bukkit.getOnlinePlayers().size());
		final Score enjeu = objetctive.getScore("§6| §cEn jeu : §f" + nb);
		final Score vide = objetctive.getScore("");
		final Score bar = objetctive.getScore("§6§l――――――――――――――――――");
		final Score pub = objetctive.getScore("§f§lerizia.fr");

		bar2.setScore(11);
		vide3.setScore(10);
		grade.setScore(9);
		vide4.setScore(8);
		elo.setScore(7);
		vide2.setScore(6);
		joueur.setScore(5);
		enjeu.setScore(3);
		vide.setScore(2);
		bar.setScore(0);
		pub.setScore(1);
		
		
		player.setScoreboard(scoreboard);

	}
	
	@EventHandler
	public void onTouch(InventoryClickEvent e) {
		FileConfiguration kit  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		ItemStack it = e.getCurrentItem();
		Inventory iv = e.getInventory();
		int nb = e.getSlot();
		if(e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
				
				// SI il n'est pas en partie !
				if(it == null || it.getType() == Material.AIR) return;
				
				if(it.getType() == Material.IRON_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lUnranked")) {
					if(main.getInstance().online.contains(player)) return;
					if(player.getGameMode() == GameMode.CREATIVE) return;
					e.setCancelled(true);
					player.closeInventory();
					player.openInventory(main.getInstance().inv.getInv(player, "unranked"));
					return;
				}
				if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRanked")) {
					if((main.getInstance().online.contains(player)) || ((main.getInstance().ranonline.contains(player)))) return;
					if(player.getGameMode() == GameMode.CREATIVE) return;
					e.setCancelled(true);
					player.closeInventory();
					player.openInventory(main.getInstance().ran.inv.OpenRan(player));
					return;
				}
				if(it.getType() == Material.BOOK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lEditer un kit")) {
					if(main.getInstance().online.contains(player)) return;
					if(player.getGameMode() == GameMode.CREATIVE) return;
					e.setCancelled(true);
					player.closeInventory();
					player.openInventory(main.ed.Inv(player));
					return;
				}
				if(it.getType() == Material.BARRIER && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter le groupe")) {
					e.setCancelled(true);
					player.closeInventory();
					main.groupe.onLeave(player);
					return;
				}
				if(it.getType() == Material.IRON_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6§l1vs1")) {
					e.setCancelled(true);
					player.closeInventory();
					player.openInventory(main.groupe.onDuel(player));
					return;
				}
				if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lffa")) {
					e.setCancelled(true);
					player.closeInventory();
					player.openInventory(main.groupe.onFfa(player));
					return;
				}
				if(it.getType() == Material.REDSTONE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter la fil d'attente")) {
					if((!(main.ranplay.containsKey(player))) && (!(main.play.containsKey(player)))){
						player.sendMessage("Erreur interne nous vous invitons a vous déconnecter !");
						return;
					}
					String id = "";
					if(main.ranplay.containsKey(player)) {
						id = main.ranplay.get(player);
						main.ranplay.remove(player);
						List<Player> li = main.rankit.get(id);
						li.remove(player);
					}else {
						id = main.play.get(player);
						main.play.remove(player);
						List<Player> li = main.kit.get(id);
						li.remove(player);
					}
					player.getInventory().clear();

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
					player.getInventory().setItem(4, main.ed.Item());
					
					player.sendMessage("§6§lVous avez quitté la fil d'attente du " + main.getConfig().getString("kit." + id + ".name") + " !");
					
					
					
					return;
				}
				if(iv.getName().equalsIgnoreCase("§6§lMode Unranked")) {
					if(main.getInstance().online.contains(player)) return;
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					int nombre = 0;
					Boolean bool = false;
					int nomb = 0;
					int numb = 0;
					if(nb < 18) {
						numb = nb-11;
					}else if(nb < 27) {
						numb = nb-16;
					}else if(nb < 27) {
						numb = nb-16;
					}
					else {
						numb = nb-21;
					}
					
					while(!bool) {
						nombre++;
						if(kit.getBoolean("kit." + nombre + ".act")) {
							nomb++;
							if(nomb == numb) {
								bool = true;
							}
						}
					}
					
					player.closeInventory();
					main.arene.onGet("" + nombre, player);
					
				}
				if(iv.getName().equalsIgnoreCase("§6§lKit Editor")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					if(it.getType() == Material.WOOL && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a§lAjouter")) {
						//File configurationFile = new File(main.getDataFolder(), "config.yml");

						int num = kit.getInt("kitnumber");
						int number = num +1;
						/**try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("kit." + number + ".act" , false);
				            try {
								main.getConfig().save(configurationFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
						try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("kitnumber" , number);
				            try {
								main.getConfig().save(configurationFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }**/
						player.closeInventory();
						player.openInventory(main.getInstance().inv.ModInv("" + number, player));
						return;
					}
					
					
					int nombre = 0;
					if(nb < 18) {
						nombre = nb-11;
					}else if(nb < 27) {
						nombre = nb-16;
					}else if(nb < 27) {
						nombre = nb-16;
					}
					else {
						nombre = nb-21;
					}
					player.closeInventory();
					player.openInventory(main.getInstance().inv.InvCreat("" + nombre, player));
					
				}if(iv.getName().contains("§6§lEdit Kit")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					String id = iv.getName().replace("§6§lEdit Kit ", "");
					
					if(nb == 24) {
						player.closeInventory();
						player.openInventory(main.getInstance().inv.ModInv(id,player));

					}else if(nb == 20) {
						if(kit.getBoolean("kit." + id + ".act") == true) {
							
					        main.getConnect().actKit(false, id, false);

							try {
					            try {
					            	kit.load(main.getFile("kit"));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            kit.set("kit." + id + ".act" , false);
					            try {
					            	kit.save(main.getFile("kit"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							
							player.sendMessage("§6§lLe kit a été désactivé !");
							
							main.kit.remove(id);
							main.kitingame.remove(id);

							
						}else if(kit.getBoolean("kit." + id + ".act") == false) {
					        main.getConnect().actKit(false, id, true);
							try {
					            try {
									kit.load(main.getFile("kit"));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            kit.set("kit." + id + ".act" , true);
					            try {
					            	kit.save(main.getFile("kit"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							
							List<Player> pl = new ArrayList<>();
							main.kit.put(id, pl);
							List<Player> plu = new ArrayList<>();
							main.kitingame.put(id, plu);
							
							player.sendMessage("§6§lLe kit a été activé !");
						}
						player.closeInventory();
					}else if(nb == 30){
						if(kit.getBoolean("kit." + id + ".act") == false) {
							player.sendMessage("§cIl faut s'abord activer le kit !");
							player.closeInventory();
							return;
						}
						player.closeInventory();
						player.openInventory(main.getInstance().ran.inv.EditRan(id, player));
					}else if(nb == 32){
						player.closeInventory();
						player.openInventory(main.inv.setProperties(player, id));
						return;
					}
					else if(nb == 40) {

						player.closeInventory();
						player.openInventory(main.getInstance().inv.getInv(player, "kiteditor"));
					}
				}if(iv.getName().contains("§6§lEdit Item Kit")) {
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) e.setCancelled(true);
					String id = iv.getName().replace("§6§lEdit Item Kit ", "");
					ItemStack item = new ItemStack(Material.DIRT, 1);
					if(nb == 45) {
						e.setCancelled(true);
						if(kit.getItemStack("kit." + id + "item") == null) {
							
							
						}
						player.closeInventory();
						player.openInventory(main.getInstance().inv.getInv(player, "kiteditor"));
					}else if(nb == 53) {
				        try {
				            try {
				            	kit.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kit.set("kit." + id + ".item", item);
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
						if(iv.getItem(49) == null) {
							e.setCancelled(true);
							player.sendMessage("§cVous devez remplir la partie de l'item représentant du kit !");
							return;
						}
						


						
						e.setCancelled(true);
						int numb = 0;
						int nomb2 = 0;
						while(numb != 9) {
							nomb2 = numb + 1;
					        try {
					            try {
					            	kit.load(main.getFile("kit"));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            kit.set("kit." + id + ".inventory." + nomb2, iv.getItem(numb));
					            try {
					            	kit.save(main.getFile("kit"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							numb++;
						}
						int nomb = 0;
						while(numb != 36) {
							nomb2 = numb + 1;
					        try {
					            try {
					            	kit.load(main.getFile("kit"));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            nomb = numb + 9;
					            kit.set("kit." + id + ".inventory." + nomb2, iv.getItem(nomb));
					            try {
					            	kit.save(main.getFile("kit"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							numb++;
						}
				            kit.set("kit." + id + ".item", iv.getItem(49));
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}

				        
				        try {
				            try {
				            	kit.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kit.set("kit." + id + ".inventory.helmet", iv.getItem(9));
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }

				        try {
				            try {
				            	kit.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kit.set("kit." + id + ".inventory.chestplate", iv.getItem(10));
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
				        
				        try {
				            try {
				            	kit.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kit.set("kit." + id + ".inventory.leggings", iv.getItem(11));
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
				        
				        try {
				            try {
				            	kit.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kit.set("kit." + id + ".inventory.boots", iv.getItem(12));
				            try {
				            	kit.save(main.getFile("kit"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
				        
				        if(kit.getInt("kitnumber") <  Integer.parseInt(id)) {
					        try {
					            try {
									kit.load(main.getFile("kit"));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            kit.set("kitnumber", Integer.parseInt(id));
					            try {
					            	kit.save(main.getFile("kit"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
				        }
						player.sendMessage("§6Vous avez bien modifier le kit " + kit.getString("kit." + id + ".name"));
						player.closeInventory();
						
						main.id = id;
						main.inv.Anvil(player);
						
					}
				}if(iv.getName().contains("§6§lToutes les arènes")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.SKULL_ITEM) {
						if(nb == 53) {
							main.getar++;
							player.closeInventory();
							player.openInventory(main.inv.onArene(main.getar, player));
							
						}else if(nb == 45) {
							main.getar--;
							player.closeInventory();
							player.openInventory(main.inv.onArene(main.getar, player));

						}
						return;
					}
					else if(nb>=10) {
						int nombre = 0; 
						if(nb < 18) {
							nombre = nb - 10;
						}else if(nb < 27) {
							nombre = nb - 19;
						}else if(nb < 36) {
							nombre = nb - 28;
						}else if(nb < 45) {
							nombre = nb - 37;
						}
						InventoryAction action = e.getAction();
							int toto = 28*(main.getar-1);
							float ft1 = 0;
							float ft2 = 0;
							nombre = nombre + toto;
							nombre++;
							Location loc = null ; 
									if(action == InventoryAction.SWAP_WITH_CURSOR) {
										
									}else {
										player.sendMessage("" + nombre);
									ft1 =  main.aren.getInt("arene.all." + nombre +".player1.yaw");
									ft2 = main.aren.getInt("arene.all." + nombre +".player1.pitch");
									loc = new Location(Bukkit.getWorld(main.aren.getString("arene.all." + nombre + ".world")),main.aren.getInt("arene.all." + nombre + ".player1.x"),main.aren.getInt("arene.all." + nombre + ".player1.y"),main.aren.getInt("arene.all." + nombre + ".player1.z"), ft1, ft2);					
									player.teleport(loc);
									}
					}
				}
				
				if(iv.getName().contains("§6§lRanked Kit ")) {
					String id = iv.getName().replace("§6§lRanked Kit ", "");
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) e.setCancelled(true);
					if(nb == 20) {
						player.closeInventory();
						main.ran.act.Activ(id, player);
					}else if(nb == 24) {
						player.closeInventory();
						main.ran.act.Clear(id, player);

					}else if(nb == 40) {
					

					player.closeInventory();
					player.openInventory(main.getInstance().inv.InvCreat(id, player));
				}
				
					
				}
				if(iv.getName().contains("§c§lMode Ranked")) {
					FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;				
					if(it.getType() == Material.AIR) return;
					if(nb == 4) return;
					
					int nombre = 0;
					Boolean bool = false;
					int nomb = 0;
					int numb = 0;
					if(nb < 26) {
						numb = nb-19;
					}else {
						numb = nb-23;
					}
					
					while(!bool) {
						nombre++;
						if(kite.getBoolean("kit." + nombre + ".ranked")) {
							nomb++;
							if(nomb == numb) {
								bool = true;
							}
						}
					}
					
					player.closeInventory();
					main.arene.onRanGet("" + nombre, player);
				}if(iv.getName().contains("§6§lInventory perdant ")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;				
					if(it.getType() == Material.AIR) return;
					
					String id = iv.getName().replace("§6§lInventory perdant ", "");
					
					if(nb == 45) {
						player.closeInventory();
						return;
					}
					if(nb == 49) {
						for(String st : main.getInstance().mess.stat.get(id)) {
							player.sendMessage(st);
						}
						return;
					}
					if(nb == 53) {
						player.closeInventory();
						player.openInventory(main.getInstance().mess.getinv.get(id));
						return;
					}
				}if(iv.getName().contains("§6§lInventory gagnant ")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;				
					if(it.getType() == Material.AIR) return;
					
					String id = iv.getName().replace("§6§lInventory gagnant ", "");
					
					if(nb == 45) {
						player.closeInventory();
						return;
					}
					if(nb == 49) {
						for(String st : main.getInstance().mess.stat.get(id)) {
							player.sendMessage(st);
						}
						return;
					}
					if(nb == 53) {
						player.closeInventory();
						player.openInventory(main.getInstance().mess.getinv2.get(id));
						return;
					}
				}if(iv.getName().contains("§6§lDemande en duel de ")) {
				 	e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					if(nb == 11) {
						main.duele.onStart(player);
						player.closeInventory();
					}else if(nb == 15) {
						Player p = main.duel.get(player);
						p.sendMessage("§c§l" + player.getName() + " a refusé votre demande en duel !");
						main.duel.remove(player);
						main.duelkit.remove(player);
						player.closeInventory();
					}
					
					
				}if(iv.getName().contains("§6§lDemande en duel à ")) {
					 Player play = Bukkit.getPlayer(iv.getName().replace("§6§lDemande en duel à ", ""));
					 	e.setCancelled(true);
						if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
						if(it.getType() == Material.AIR) return;
						
						int nombre = 0;
						Boolean bool = false;
						int nomb = 0;
						int numb = 0;
						if(nb < 26) {
							numb = nb-19;
						}else {
							numb = nb-23;
						}
						
						while(!bool) {
							nombre++;
							if(kit.getBoolean("kit." + nombre + ".act")) {
								nomb++;
								if(nomb == numb) {
									bool = true;
								}
							}
						}
						
						player.closeInventory();
					
					 main.duel.put(play, player);
					 main.duelkit.put(play, "" + nombre);
					 player.sendMessage("§a§lL'invitation de " + play.getName() + " a bien été faite !");
					 play.openInventory(main.inv.getDuel(play));
					 
				}if(iv.getName().equalsIgnoreCase("§6§lModifier un kit")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					int nombre = 0;
					Boolean bool = false;
					int nomb = 0;
					int numb = 0;
					if(nb < 18) {
						numb = nb-9;
					}
					else if(nb < 27){
						numb = nb-11;
					}else if(nb < 36){
						numb = nb-13;
					}else {
						numb = nb-14;
					}
					
					while(!bool) {
						nombre++;
						if(kit.getBoolean("kit." + nombre + ".act")) {
							nomb++;
							if(nomb == numb) {
								bool = true;
							}
						}
					}
					
					main.ed.onChange(player, "" + nomb);


				}if(iv.getName().contains("§6§lOptions avancées ")) {
					String id = iv.getName().replace("§6§lOptions avancées " , "");
					e.setCancelled(true);
					if(nb == 20) {
						if(kit.getBoolean("kit." + id + ".prop.sumo"))return;
						if(kit.getBoolean("kit." + id + ".prop.parcour")) {
							 kit.set("kit." + id + ".prop.parcour", false);
						}
						
						 kit.set("kit." + id + ".prop.sumo", true);
						 try {
			            	kit.save(main.getFile("kit"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						 player.sendMessage("§a§lLe mode sumo a bien été activer !");
						 player.closeInventory();
						 return;
					}
					if(nb == 22) {
						if(kit.getBoolean("kit." + id + ".prop.sumo")) {
							 kit.set("kit." + id + ".prop.sumo", false);
						}
						if(kit.getBoolean("kit." + id + ".prop.parcour")) {
							 kit.set("kit." + id + ".prop.parcour", false);
						}
						 try {
			            	kit.save(main.getFile("kit"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						 player.closeInventory();
						 return;
					}
					if(nb == 24) {
						if(kit.getBoolean("kit." + id + ".prop.parcour"))return;
						if(kit.getBoolean("kit." + id + ".prop.sumo")) {
							 kit.set("kit." + id + ".prop.sumo", false);
						}
						
						 kit.set("kit." + id + ".prop.parcour", true);
						 try {
			            	kit.save(main.getFile("kit"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						 player.sendMessage("§a§lLe mode parcours a bien été activer !");
						 player.closeInventory();
						 return;
					}

				}if(iv.getName().equalsIgnoreCase("§6§l1vs1")) {
					if(main.getInstance().online.contains(player) || main.getInstance().ranonline.contains(player)) return;
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					if(main.groupe.getGrp.get(player).size() == 1) {
						player.closeInventory();
						return;
					}
					
					int nombre = 0;
					Boolean bool = false;
					int nomb = 0;
					int numb = 0;
					if(nb < 18) {
						numb = nb-11;
					}else if(nb < 27) {
						numb = nb-16;
					}else if(nb < 27) {
						numb = nb-16;
					}
					else {
						numb = nb-21;
					}
					
					while(!bool) {
						nombre++;
						if(kit.getBoolean("kit." + nombre + ".act")) {
							nomb++;
							if(nomb == numb) {
								bool = true;
							}
						}
					}
					
					player.closeInventory();
					main.groupe.ouDuel( player,"" + nombre);
					return;
				}if(iv.getName().equalsIgnoreCase("§6§lffa")) {
					if(main.getInstance().online.contains(player) || main.getInstance().ranonline.contains(player)) return;
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					if(main.groupe.getGrp.get(player).size() == 1) {
						player.closeInventory();
						return;
					}
					
					int nombre = 0;
					Boolean bool = false;
					int nomb = 0;
					int numb = 0;
					if(nb < 18) {
						numb = nb-11;
					}else if(nb < 27) {
						numb = nb-16;
					}else if(nb < 27) {
						numb = nb-16;
					}
					else {
						numb = nb-21;
					}
					
					while(!bool) {
						nombre++;
						if((kit.getBoolean("kit." + nombre + ".act")) && (!kit.getBoolean("kit." + nombre + ".prop.sumo")) && (!kit.getBoolean("kit." + nombre + ".prop.parcour"))) {
							nomb++;
							if(nomb == numb) {
								bool = true;
							}
						}
					}
					
					player.closeInventory();
					main.groupe.onFfa( player,"" + nombre);
					return;
				}

			}

		}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if(main.att.contains(player)) {
			e.setCancelled(true);
			return;
		}
		if(!(main.getInstance().online.contains(player))) {
			if(player.getGameMode() == GameMode.CREATIVE) return;
			player.sendMessage("§4Vous ne povez pas faire cela !");
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInterract(PlayerInteractEvent e) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		Player player = e.getPlayer();
		ItemStack it = e.getItem();
		
		if(it == null) {
			if(e.getClickedBlock() == null) return;
			if((e.getClickedBlock().getType() == Material.SIGN )|| (e.getClickedBlock().getTypeId() == 68) || (e.getClickedBlock().getTypeId() == 63)) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(sign.getLine(0).equalsIgnoreCase("[Sauvegarder]")) {
					main.ed.isChange(player);
					player.sendMessage("§6§lLe kit fut changer !");
					return;
				}
			}else {
				return;
			}
		}
		


		

		if((!(main.getInstance().online.contains(player))) && (!(main.getInstance().ranonline.contains(player)))) {
			if(player.getGameMode() == GameMode.CREATIVE) return;
			e.setCancelled(true);
			// SI il n'est pas en partie !
			if(it.getType() == Material.IRON_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lUnranked")) {
				if(player.getGameMode() == GameMode.CREATIVE) return;
				e.setCancelled(true);
				player.openInventory(main.getInstance().inv.getInv(player, "unranked"));
			}
			
			else if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRanked")) {
				if(player.getGameMode() == GameMode.CREATIVE) return;
				e.setCancelled(true);
				player.openInventory(main.getInstance().ran.inv.OpenRan(player));
			}
			else if(it.getType() == Material.BOOK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lEditer un kit")) {
				if(player.getGameMode() == GameMode.CREATIVE) return;
				e.setCancelled(true);
				player.closeInventory();
				player.openInventory(main.ed.Inv(player));
				return;
			}
			else if(it.getType() == Material.BARRIER && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter le groupe")) {
				main.groupe.onLeave(player);
				main.onGive(player);
				return;
			}
			else if(it.getType() == Material.IRON_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§6§l1vs1")) {
				player.openInventory(main.groupe.onDuel(player));
				return;
			}
			if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lffa")) {
				player.openInventory(main.groupe.onFfa(player));
				return;
			}
			else if(it.getType() == Material.REDSTONE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter la fil d'attente")) {
				if((!(main.ranplay.containsKey(player))) && (!(main.play.containsKey(player)))){
					player.sendMessage("Erreur interne nous vous invitons a vous déconnecter !");
					return;
				}
				String id = "";
				if(main.ranplay.containsKey(player)) {
					id = main.ranplay.get(player);
					main.ranplay.remove(player);
					List<Player> li = main.rankit.get(id);
					li.remove(player);
				}else {
					id = main.play.get(player);
					main.play.remove(player);
					List<Player> li = main.kit.get(id);
					li.remove(player);
				}
				
				main.onGive(player);
				
				player.sendMessage("§6§lVous avez quitté la fil d'attente du " + kite.getString("kit." + id + ".name") + " !");
				
				return;
			}
			else if(e.getClickedBlock() == null) return;
			else if((e.getClickedBlock().getType() == Material.SIGN )|| (e.getClickedBlock().getTypeId() == 68) || (e.getClickedBlock().getTypeId() == 63)) {
				Sign sign = (Sign) e.getClickedBlock().getState();
			
				if(sign.getLine(0).equalsIgnoreCase("[Sauvegarder]")) {
					main.ed.isChange(player);
					player.sendMessage("§6§lLe kit fut changer !");
					return;
				}
			}
		}
		
		
	}
	
	@EventHandler
	public void onFeed(FoodLevelChangeEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		if(main.online.contains(player)) return;
		e.setFoodLevel(20);
		
		
	}
	
	@EventHandler
	public void onQui(InventoryCloseEvent e) {
		if(!(e.getPlayer() instanceof Player)) return;
		Player player = (Player) e.getPlayer();
		if(e.getInventory().getName().contains("§6§lDemande en duel de ")) {
			if(main.duel.containsKey(player)) {
				Player p = main.duel.get(player);
				p.sendMessage("§c§l" + player.getName() + " a refusé votre demande en duel !");
				main.duel.remove(player);
				main.duelkit.remove(player);
			}
		}
	}
	
	

}
