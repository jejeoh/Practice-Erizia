package fr.jejeoh.practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.jejeoh.practice.AnvilGUI.AnvilClickEvent;

public class InventoryManager {
	
	private Base main;
	
	public InventoryManager (Base main) {
		this.main = main;
	}
	
	
	
	
	public Inventory getInv(Player player, String str) {
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		if(str.equalsIgnoreCase("unranked")) {
			
			if(main.getConfig().getInt("kitnumber") <= 5) {
				inv = Bukkit.createInventory(player, 45, "§6§lMode Unranked");
			}else if(main.getConfig().getInt("kitnumber") >= 6) {
				inv = Bukkit.createInventory(player, 54, "§6§lMode Unranked");
			}
			int nb = 0;
			int numb = 0;
			ItemStack kit = null;
			while(nb != main.getConfig().getInt("kitnumber")) {
				nb++;
				if(main.getConfig().getBoolean("kit." + nb + ".act") == true) {
					
					List<Player> ingame = main.kitingame.get("" + nb);
					List<Player> game = main.kit.get("" + nb);
					
					kit = main.getConfig().getItemStack("kit." + nb + ".item");
					ItemMeta kit2 = kit.getItemMeta();
					String stre = main.getConfig().getString("kit." + nb + ".name");
					kit2.setDisplayName("§6§l" + stre);
					kit2.setLore(Arrays.asList("§cJoueur en combat : " + ingame.size(), "§aJoueur en liste d'attente : " + game.size()));
					kit2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					if(game.contains(player)) {
						kit2.addEnchant(Enchantment.DURABILITY, 1, true);
					}else {
						kit2.removeEnchant(Enchantment.DURABILITY);
					}
					kit.setItemMeta(kit2);
					numb++;
					if( nb <= 5) {
						inv.setItem(19+numb, kit);
					}else if( nb >= 6){
						inv.setItem(28+numb, kit);
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

		}

		if(str.equalsIgnoreCase("kiteditor")) {
			
			if(main.getConfig().getInt("kitnumber") <= 5) {
				inv = Bukkit.createInventory(player, 45, "§6§lKit Editor");
			}else if(main.getConfig().getInt("kitnumber") >= 6) {
				inv = Bukkit.createInventory(player, 54, "§6§lKit Editor");
			}
			int nb = 0;
			ItemStack ktt = null;
			while(nb != main.getConfig().getInt("kitnumber")) {
				nb++;
				ktt = main.getConfig().getItemStack("kit." + nb + ".item");

				ItemMeta kit = ktt.getItemMeta();
				String stre = main.getConfig().getString("kit." + nb + ".name");
				kit.setDisplayName("§6§l" + stre);
				kit.setLore(null);
				kit.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				if(ktt.getItemMeta().hasEnchants()) {
					kit.removeEnchant(Enchantment.DURABILITY);
				}
				ktt.setItemMeta(kit);
				if( nb <= 5) {
					inv.setItem(19+nb, ktt);
				}else if( nb >= 6){
					inv.setItem(28+nb, ktt);
				}
			}
			
			ItemStack act = new ItemStack(Material.WOOL, 1, (byte) 5);
			ItemMeta act2 = act.getItemMeta();
			act2.setDisplayName("§7§lAjouter");
			act.setItemMeta(act2);
			
			
			
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
				
				inv.setItem(40, act);
	
				inv.setItem(42, gpvert);
				inv.setItem(43, gpvert);
				inv.setItem(44, gpvert);
			}else {
				inv.setItem(36, gpvert);
				inv.setItem(44, gpvert);

				inv.setItem(45, gpvert);
				inv.setItem(46, gpvert);
				inv.setItem(47, gpvert);

				inv.setItem(49, act);
				
				inv.setItem(51, gpvert);
				inv.setItem(52, gpvert);
				inv.setItem(53, gpvert);
			}

		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return inv;
	}
	
	public Inventory InvCreat(int nb, Player player) {
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack it = main.getConfig().getItemStack("kit." + nb + ".item");
		ItemMeta it2 = it.getItemMeta();
		it2.setDisplayName("§c§l" + main.getConfig().getString("kit." + nb + ".name"));
		it.setItemMeta(it2);
		
		ItemStack good = new ItemStack(Material.WOOL, 1, (byte) 8);
		ItemMeta good2 = good.getItemMeta();
		good2.setDisplayName("§7§lModifier");
		good.setItemMeta(good2);
		
		ItemStack no = null;	
		
		if(main.getConfig().getBoolean("kit." + nb + ".act") == true) {
		no = new ItemStack(Material.WOOL, 1, (byte) 14);
		ItemMeta no2 = no.getItemMeta();
		no2.setDisplayName("§c§lDesactiver");
		no.setItemMeta(no2);
		
		}else {
			no = new ItemStack(Material.WOOL, 1, (byte) 5);
			ItemMeta no2 = no.getItemMeta();
			no2.setDisplayName("§a§lActiver");
			no.setItemMeta(no2);
		}

		ItemStack back = new ItemStack(Material.BARRIER, 1);
		ItemMeta back2 = back.getItemMeta();
		back2.setDisplayName("§2§lBack");
		back.setItemMeta(back2);
		
		Inventory inv = null;
		inv = Bukkit.createInventory(player, 45, "§6§lEdit Kit " +  nb);
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);

		
		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(13, it);
		inv.setItem(17, gpvert);
		
		inv.setItem(24, good);
		inv.setItem(20, no);


		inv.setItem(27, gpvert);
		inv.setItem(35, gpvert);

		inv.setItem(36, gpvert);
		inv.setItem(37, gpvert);
		inv.setItem(38, gpvert);
		
		inv.setItem(40, back);

		inv.setItem(42, gpvert);
		inv.setItem(43, gpvert);
		inv.setItem(44, gpvert);

		
		return inv;
	}
	public Inventory ModInv(String nb, Player player) {
		Inventory inv = null;
		inv = Bukkit.createInventory(player, 54, "§6§lEdit Item Kit " +  nb);

		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack back = new ItemStack(Material.BARRIER, 1);
		ItemMeta back2 = back.getItemMeta();
		back2.setDisplayName("§c§lAnnulé");
		back.setItemMeta(back2);
		
		ItemStack good = new ItemStack(Material.WOOL, 1, (byte) 8);
		ItemMeta good2 = good.getItemMeta();
		good2.setDisplayName("§7§lConfirmer");
		good.setItemMeta(good2);
		
		ItemStack item = null;
		
		int bo = 0;
		while(bo != 36) {
			bo++;
			item = main.getConfig().getItemStack("kit." + nb + ".inventory." + bo);
			if(bo <= 9) {
				inv.setItem(bo-1, item);
			}else {
				inv.setItem(bo+8, item);
				
			}
		}
		item = main.getConfig().getItemStack("kit." + nb + ".item");
		

		inv.setItem(13, gpvert);
		inv.setItem(14, gpvert);
		inv.setItem(15, gpvert);
		inv.setItem(16, gpvert);
		inv.setItem(17, gpvert);

		inv.setItem(45, back);
		inv.setItem(46, gpvert);
		inv.setItem(47, gpvert);
		inv.setItem(48, gpvert);
		inv.setItem(49, item);
		inv.setItem(50, gpvert);
		inv.setItem(51, gpvert);
		inv.setItem(52, gpvert);
		inv.setItem(53, good);

		item = main.getConfig().getItemStack("kit." + nb + ".inventory.helmet");
		inv.setItem(9, item);

		item = main.getConfig().getItemStack("kit." + nb + ".inventory.chestplate");
		inv.setItem(10, item);

		item = main.getConfig().getItemStack("kit." + nb + ".inventory.leggings");
		inv.setItem(11, item);
		
		item = main.getConfig().getItemStack("kit." + nb + ".inventory.boots");
		inv.setItem(12, item);
		
		return inv;
	}
	
	public void Anvil(Player player) {
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);
                      player.sendMessage("Le nom a été sauvegardé  " + event.getName());
                      
  					File configurationFile = new File(main.getDataFolder(), "config.yml");

                      
                      try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("kit." + main.id + ".name" , event.getName());
				            try {
								main.getConfig().save(configurationFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
                  }else{
                      event.setWillClose(false);
                      event.setWillDestroy(false);
                  }
				
			}
		});
		
		
		ItemStack it = new ItemStack(Material.PAPER, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("Choise Name");
		it.setItemMeta(im);
		
        gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, it);
        
        gui.open();

		
		return;
	}
	
	public void AnvilArene(Player player) {
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);                      
  					File configurationFile = new File(main.getDataFolder(), "config.yml");

                      
                      try {
				            try {
								main.getConfig().load(configurationFile);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.getConfig().set("arene.all." + main.id + ".name" , event.getName());
							main.getConfig().set("arene.all." + main.id + ".act" , true);
							main.arene.AddArene(main.id);
				            try {
								main.getConfig().save(configurationFile);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        }catch (InvalidConfigurationException exe) {
				            exe.printStackTrace();
				        }
                      player.sendMessage("§6§lLe nom " + event.getName() + " à été sauvegarder !");

                  }else{
                      event.setWillClose(false);
                      event.setWillDestroy(false);
                  }
				
			}
		});
		
		
		ItemStack it = new ItemStack(Material.PAPER, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("Choise Name");
		it.setItemMeta(im);
		
        gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, it);
        
        gui.open();

		
		return;

	}
	
	public void onAddArene(Player player) {
			File configurationFile = new File(main.getDataFolder(), "config.yml");

		if(main.etat == 0) {
			main.etat = 1;
			int id = main.getConfig().getInt("arene.number") + 1;
            try {
		            try {
						main.getConfig().load(configurationFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		            
		            player.sendMessage("" + id);
		            
					main.getConfig().set("arene.all." + id + ".player1.x" , player.getLocation().getBlockX());
					main.getConfig().set("arene.all." + id + ".player1.y" , player.getLocation().getBlockY());
					main.getConfig().set("arene.all." + id + ".player1.z" , player.getLocation().getBlockZ());
										
					main.getConfig().set("arene.all." + id + ".world" , player.getWorld().getName());

					main.getConfig().set("arene.number" , id);


					main.getConfig().set("arene.all." + id + ".player2.x" , 0);
					main.getConfig().set("arene.all." + id + ".player2.y" , 0);
					main.getConfig().set("arene.all." + id + ".player2.z" , 0);

					main.getConfig().set("arene.all." + id + ".act" , false);
		            try {
						main.getConfig().save(configurationFile);
						player.sendMessage("§6§lLe premier point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }catch (InvalidConfigurationException exe) {
		            exe.printStackTrace();
					player.sendMessage("§c§lLe premier point à eu un problème merci de contacter un administrateur");
					main.etat = 0;
		        }
            
		}else {
			main.etat = 0;
			int id = main.getConfig().getInt("arene.number");
            try {
		            try {
						main.getConfig().load(configurationFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		            
		            player.sendMessage("" + id);

					main.getConfig().set("arene.all." + id + ".player2.x" , player.getLocation().getBlockX());
					main.getConfig().set("arene.all." + id + ".player2.y" , player.getLocation().getBlockY());
					main.getConfig().set("arene.all." + id + ".player2.z" , player.getLocation().getBlockZ());

					main.getConfig().set("arene.all." + id + ".act" , true);
		            try {
						main.getConfig().save(configurationFile);
						player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
						main.id = "" + id;
						main.inv.AnvilArene(player);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }catch (InvalidConfigurationException exe) {
		            exe.printStackTrace();
					player.sendMessage("§c§lLe deuxième point à eu un problème merci de contacter un administrateur");
					main.etat = 0;
		        }
		}
		
		return;
	}
	//main.getConfig().getInt("arene.number")
	public Inventory onArene(int id, Player player) {
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack fleche = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		Inventory inv = null;
		inv = Bukkit.createInventory(player, 54, "§6§lToutes les arènes");
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);
		inv.setItem(3, gpvert);
		inv.setItem(4, gpvert);
		inv.setItem(5, gpvert);
		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);
		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);
		inv.setItem(18, gpvert);
		inv.setItem(26, gpvert);
		inv.setItem(27, gpvert);
		inv.setItem(35, gpvert);
		inv.setItem(36, gpvert);
		inv.setItem(44, gpvert);
		if(id != 1) {
			SkullMeta sk = (SkullMeta) fleche.getItemMeta();
			sk.setOwner("MHF_ArrowLeft");
			sk.setDisplayName("§f§lRevenir à la page précedente");
			fleche.setItemMeta(sk);
			inv.setItem(45, fleche);
		}else {
			inv.setItem(45, gpvert);
		}
		inv.setItem(46, gpvert);
		inv.setItem(47, gpvert);
		inv.setItem(48, gpvert);
		inv.setItem(49, gpvert);
		inv.setItem(50, gpvert);
		inv.setItem(51, gpvert);
		inv.setItem(52, gpvert);
		if(main.getConfig().getInt("arene.number") > 28*id) {
			SkullMeta sk = (SkullMeta) fleche.getItemMeta();
			sk.setOwner("MHF_ArrowRight");
			sk.setDisplayName("§f§lAller a la page suivante");
			fleche.setItemMeta(sk);
			inv.setItem(53, fleche);
		}else {
			inv.setItem(53, gpvert);
		}
		
		int nombre = 0;
		
		ItemStack it = null;
		if(main.getConfig().getInt("arene.number") < 28*id) {
			int base = id-1;
			int toto = 28*base;
			int obj = 0;
			while(nombre != main.getConfig().getInt("arene.number") - toto) {
				nombre++;
				obj = nombre +toto;
				if(main.getConfig().getBoolean("arene.all." + obj + ".act"))it = new ItemStack(Material.WOOL, 1,(byte) 5);
				else it = new ItemStack(Material.WOOL, 1,(byte) 14);
				
				ItemMeta ite = it.getItemMeta();
				ite.setDisplayName("§6§l" + main.getConfig().getString("arene.all." + obj + ".name"));
				it.setItemMeta(ite);

				if(nombre <= 7) {
					inv.setItem(nombre + 9, it);
				}else if(nombre <= 14) {
					inv.setItem(nombre + 11, it);
				}else if(nombre <= 21) {
					inv.setItem(nombre + 13, it);
				}else {
					inv.setItem(nombre + 15, it);
				}
			}

		}else {
			while(nombre != 28) {
				nombre++;
				
				
				
				if(nombre <= 7) {
					inv.setItem(nombre + 9, it);
				}else if(nombre <= 14) {
					inv.setItem(nombre + 11, it);
				}else if(nombre <= 21) {
					inv.setItem(nombre + 13, it);
				}else {
					inv.setItem(nombre + 15, it);
				}
			}
		}

		
		return inv;
	}

	
}
