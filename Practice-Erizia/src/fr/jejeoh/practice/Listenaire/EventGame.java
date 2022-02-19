package fr.jejeoh.practice.Listenaire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;

public class EventGame implements Listener {

	
	private Base main;
	
	
	public EventGame(Base main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		//main.getConnect().createAccount(player);
		
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
		
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		player.getInventory().setItem(0, unranked);
		player.getInventory().setItem(1, ranked);

		player.updateInventory();
		
		player.setHealth(20);
		
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
		player.teleport(loc);

	}
	
	@EventHandler
	public void onTouch(InventoryClickEvent e) {
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
					if(main.getInstance().online.contains(player)) return;
					if(player.getGameMode() == GameMode.CREATIVE) return;
					e.setCancelled(true);
					player.sendMessage("§cPas encore activé");
					return;
				}
				if(iv.getName().equalsIgnoreCase("§6§lMode Unranked")) {
					if(main.getInstance().online.contains(player)) return;
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					int nombre = 0;
					nombre = nb - 19;
					Boolean bool = false;
					
					while(!bool) {
						if(main.getConfig().getBoolean("kit." + nombre + ".act")) {
							bool = true;
						}else {
							nombre++;
						}
					}
					
					player.closeInventory();
					main.arene.onGet("" + nombre, player);
					
					}
				if(iv.getName().equalsIgnoreCase("§6§lKit Editor")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					
					if(it.getType() == Material.WOOL && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§7§lAjouter")) {
						File configurationFile = new File(main.getDataFolder(), "config.yml");

						int num = main.getConfig().getInt("kitnumber");
						int number = num +1;
						try {
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
				        }
						player.closeInventory();
						player.openInventory(main.getInstance().inv.ModInv("" + number, player));
						return;
					}
					
					
					int nombre = 0;
					if(nb <= 5) {
						nombre = nb - 19;
					}else if(nb >= 6) {
						nombre = nb - 19;
					}
					player.closeInventory();
					player.openInventory(main.getInstance().inv.InvCreat(nombre, player));
					
				}if(iv.getName().contains("§6§lEdit Kit")) {
					e.setCancelled(true);
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) return;
					if(it.getType() == Material.AIR) return;
					File configurationFile = new File(main.getDataFolder(), "config.yml");
					String id = iv.getName().replace("§6§lEdit Kit ", "");
					
					if(nb == 24) {
						player.closeInventory();
						player.openInventory(main.getInstance().inv.ModInv(id,player));

					}else if(nb == 20) {
						if(main.getConfig().getBoolean("kit." + id + ".act") == true) {
							try {
					            try {
									main.getConfig().load(configurationFile);
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								main.getConfig().set("kit." + id + ".act" , false);
					            try {
									main.getConfig().save(configurationFile);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							
							player.sendMessage("§6§lLe kit a été désactivé !");
						}else if(main.getConfig().getBoolean("kit." + id + ".act") == false) {
							try {
					            try {
									main.getConfig().load(configurationFile);
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								main.getConfig().set("kit." + id + ".act" , true);
					            try {
									main.getConfig().save(configurationFile);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							player.sendMessage("§6§lLe kit a été activé !");
						}
						player.closeInventory();
					}else if(nb == 40) {
						player.closeInventory();
						player.openInventory(main.getInstance().inv.getInv(player, "kiteditor"));
					}
				}if(iv.getName().contains("§6§lEdit Item Kit")) {
					File configurationFile = new File(main.getDataFolder(), "config.yml");
					if((it.getType() == Material.STAINED_GLASS_PANE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(" "))) e.setCancelled(true);;
					String id = iv.getName().replace("§6§lEdit Item Kit ", "");
					ItemStack item = new ItemStack(Material.DIRT, 1);
					if(nb == 45) {
						e.setCancelled(true);
						if(main.getConfig().getItemStack("kit." + id + "item") == null) {
							
							
						}
						player.closeInventory();
						player.openInventory(main.getInstance().inv.getInv(player, "kiteditor"));
					}else if(nb == 53) {
				        try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("kit." + id + ".item", item);
				            try {
								main.getConfig().save(configurationFile);
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
									main.getConfig().load(configurationFile);
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								main.getConfig().set("kit." + id + ".inventory." + nomb2, iv.getItem(numb));
					            try {
									main.getConfig().save(configurationFile);
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
									main.getConfig().load(configurationFile);
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            nomb = numb + 9;
								main.getConfig().set("kit." + id + ".inventory." + nomb2, iv.getItem(nomb));
					            try {
									main.getConfig().save(configurationFile);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }catch (InvalidConfigurationException exe) {
					            exe.printStackTrace();
					        }
							numb++;
						}
				        try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("kit." + id + ".item", iv.getItem(49));
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
							main.getConfig().set("kit." + id + ".inventory.helmet", iv.getItem(9));
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
							main.getConfig().set("kit." + id + ".inventory.chestplate", iv.getItem(10));
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
							main.getConfig().set("kit." + id + ".inventory.leggings", iv.getItem(11));
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
							main.getConfig().set("kit." + id + ".inventory.boots", iv.getItem(12));
				            try {
								main.getConfig().save(configurationFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
						player.sendMessage("§6Vous avez bien modifier le kit " + main.getConfig().getString("kit." + id + ".name"));
						player.closeInventory();
						
						main.id = id;
						main.inv.Anvil(player);
						
					}
				}if(iv.getName().contains("§6§lToutes les arènes")) {
					e.setCancelled(true);
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
					}
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
	
	@EventHandler
	public void onInterract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack it = e.getItem();
		if(it == null) return;

		if(!(main.getInstance().online.contains(player))) {
			// SI il n'est pas en partie !
			if(it.getType() == Material.IRON_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lUnranked")) {
				if(player.getGameMode() == GameMode.CREATIVE) return;
				e.setCancelled(true);
				player.openInventory(main.getInstance().inv.getInv(player, "unranked"));
			}
			
			if(it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRanked")) {
				if(player.getGameMode() == GameMode.CREATIVE) return;
				e.setCancelled(true);
				player.sendMessage("§cPas encore activé");
			}
		}
		
		
	}
	

}
