package fr.jejeoh.practice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.jejeoh.practice.API.AnvilGUI;
import fr.jejeoh.practice.API.AnvilGUI.AnvilClickEvent;

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
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		if(str.equalsIgnoreCase("unranked")) {
			
			if(kite.getInt("kitnumber") <= 5) {
				inv = Bukkit.createInventory(player, 45, "§6§lMode Unranked");
			}else if(main.getConfig().getInt("kitnumber") >= 6) {
				inv = Bukkit.createInventory(player, 54, "§6§lMode Unranked");
			}
			int nb = 0;
			int numb = 0;
			ItemStack kit = null;
			while(nb != kite.getInt("kitnumber")) {
				nb++;
				if(kite.getBoolean("kit." + nb + ".act") == true) {
					
					List<Player> ingame = main.kitingame.get("" + nb);
					List<Player> game = main.kit.get("" + nb);
					
					kit = kite.getItemStack("kit." + nb + ".item");
					ItemMeta kit2 = kit.getItemMeta();
					String stre = kite.getString("kit." + nb + ".name");
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

		}

		if(str.equalsIgnoreCase("kiteditor")) {
			
			if(kite.getInt("kitnumber") <= 5) {
				inv = Bukkit.createInventory(player, 45, "§6§lKit Editor");
			}else if(kite.getInt("kitnumber") >= 6) {
				inv = Bukkit.createInventory(player, 54, "§6§lKit Editor");
			}
			int nb = 0;
			ItemStack ktt = null;
			while(nb != kite.getInt("kitnumber")) {
				nb++;
				ktt = kite.getItemStack("kit." + nb + ".item");

				ItemMeta kit = ktt.getItemMeta();
				String stre = kite.getString("kit." + nb + ".name");
				kit.setDisplayName("§6§l" + stre);
				kit.setLore(null);
				kit.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				if(ktt.getItemMeta().hasEnchants()) {
					kit.removeEnchant(Enchantment.DURABILITY);
				}
				ktt.setItemMeta(kit);
				if( nb <= 3) {
					inv.setItem(11+nb, ktt);
				}else if( nb <= 8){
					inv.setItem(16+nb, ktt);
				}else if( nb <= 13){
					inv.setItem(20+nb, ktt);
				}else{
					inv.setItem(25+nb, ktt);
				}
			}
			
			ItemStack act = new ItemStack(Material.WOOL, 1, (byte) 5);
			ItemMeta act2 = act.getItemMeta();
			act2.setDisplayName("§a§lAjouter");
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
	
	public Inventory InvCreat(String nb, Player player) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack it = kite.getItemStack("kit." + nb + ".item");
		ItemMeta it2 = it.getItemMeta();
		it2.setDisplayName("§c§l" + kite.getString("kit." + nb + ".name"));
		it.setItemMeta(it2);
		
		ItemStack ran = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta ran2 = ran.getItemMeta();
		ran2.setDisplayName("§4§lRanked");
		ran.setItemMeta(ran2);
		
		ItemStack good = new ItemStack(Material.WOOL, 1, (byte) 8);
		ItemMeta good2 = good.getItemMeta();
		good2.setDisplayName("§7§lModifier");
		good.setItemMeta(good2);
		
		ItemStack no = null;	
		
		if(kite.getBoolean("kit." + nb + ".act") == true) {
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
		
		ItemStack lol = new ItemStack(Material.NETHER_BRICK_ITEM, 1);
		ItemMeta lol2 = lol.getItemMeta();
		lol2.setDisplayName("§a§lPlus d'option");
		lol.setItemMeta(lol2);
		
		Inventory inv = null;
		inv = Bukkit.createInventory(player, 45, "§6§lEdit Kit " +  nb);
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);

		
		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(4, it);
		inv.setItem(17, gpvert);
		
		inv.setItem(24, good);
		inv.setItem(20, no);
		inv.setItem(30, ran);
		inv.setItem(32, lol);


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
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
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
			item = kite.getItemStack("kit." + nb + ".inventory." + bo);
			if(bo <= 9) {
				inv.setItem(bo-1, item);
			}else {
				inv.setItem(bo+8, item);
				
			}
		}
		item = kite.getItemStack("kit." + nb + ".item");
		

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

		item = kite.getItemStack("kit." + nb + ".inventory.helmet");
		inv.setItem(9, item);

		item = kite.getItemStack("kit." + nb + ".inventory.chestplate");
		inv.setItem(10, item);

		item = kite.getItemStack("kit." + nb + ".inventory.leggings");
		inv.setItem(11, item);
		
		item = kite.getItemStack("kit." + nb + ".inventory.boots");
		inv.setItem(12, item);
		
		return inv;
	}
	
	public void Anvil(Player player) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);
                      String name;
                      if(event.getName() == null) {
                    	  name = " ";
                      }else {
                    	  name = event.getName();
                      }
                      player.sendMessage("Le nom a été sauvegardé  " + name);
                      
                      if(kite.get("kit." + main.id + ".name") == null) {
                    	  main.getConnect().Kitadd(main.id, false, name);
                      }
                      

                      
                      try {
				            try {
				            	kite.load(main.getFile("kit"));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				            kite.set("kit." + main.id + ".name" , name);
				            try {
				            	kite.save(main.getFile("kit"));
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

                      
  						main.aren.set("arene.all." + main.id + ".name" , event.getName());
  						main.aren.set("arene.all." + main.id + ".act" , true);
							main.arene.AddArene(main.id);
				            try {
				            	main.aren.save(main.getFile("arene"));
							} catch (IOException e1) {
								e1.printStackTrace();
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
	
	public void AnvilSumo(Player player, String id) {
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);                      

                      
  						main.aren.set("sumo.all." + id + ".name" , event.getName());
  						main.aren.set("sumo.all." + id + ".act" , true);
							main.arene.pro.CreatSumo(id);
				            try {
				            	main.aren.save(main.getFile("arene"));
							} catch (IOException e1) {
								e1.printStackTrace();
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
	public void AnvilParcour(Player player, String id) {
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);                      

                      
  						main.aren.set("parcour.all." + id + ".name" , event.getName());
  						main.aren.set("parcour.all." + id + ".act" , true);
							main.arene.pro.CreatParcour(id);
				            try {
				            	main.aren.save(main.getFile("arene"));
							} catch (IOException e1) {
								e1.printStackTrace();
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
	public void AnvilFfa(Player player, String id) {
		
		AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
			
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				  if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                      event.setWillClose(true);
                      event.setWillDestroy(true);                      

                      
  						main.aren.set("ffa.all." + id + ".name" , event.getName());
  						main.aren.set("ffa.all." + id + ".act" , true);
							main.groupe.st.add("" + id);
				            try {
				            	main.aren.save(main.getFile("arene"));
							} catch (IOException e1) {
								e1.printStackTrace();
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

		if(main.etat == 0) {
			main.etat = 1;
			int id = main.aren.getInt("arene.number") + 1;
		            		            
		            main.aren.set("arene.all." + id + ".player1.x" , player.getLocation().getBlockX());
		            main.aren.set("arene.all." + id + ".player1.y" , player.getLocation().getBlockY());
					main.aren.set("arene.all." + id + ".player1.z" , player.getLocation().getBlockZ());
					main.aren.set("arene.all." + id + ".player1.yaw" , player.getLocation().getYaw());
					main.aren.set("arene.all." + id + ".player1.pitch" , player.getLocation().getPitch());

										
					main.aren.set("arene.all." + id + ".world" , player.getWorld().getName());

					main.aren.set("arene.number" , id);


					main.aren.set("arene.all." + id + ".player2.x" , 0);
					main.aren.set("arene.all." + id + ".player2.y" , 0);
					main.aren.set("arene.all." + id + ".player2.z" , 0);

					main.aren.set("arene.all." + id + ".act" , false);
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe premier point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe premier point à eu un problème merci de contacter un administrateur");
						main.etat = 0;
					}
		        
            
		}else {
			main.etat = 0;
			int id = main.aren.getInt("arene.number");
		            

					main.aren.set("arene.all." + id + ".player2.x" , player.getLocation().getBlockX());
					main.aren.set("arene.all." + id + ".player2.y" , player.getLocation().getBlockY());
					main.aren.set("arene.all." + id + ".player2.z" , player.getLocation().getBlockZ());
					main.aren.set("arene.all." + id + ".player2.yaw" , player.getLocation().getYaw());
					main.aren.set("arene.all." + id + ".player2.pitch" , player.getLocation().getPitch());

					main.aren.set("arene.all." + id + ".act" , true);
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
						main.id = "" + id;
						main.inv.AnvilArene(player);
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe deuxième point à eu un problème merci de contacter un administrateur");
					main.etat = 0;
					}
					
		}
		
		return;
	}
	public void onAddSumo(Player player) {

		if(main.sum == 0) {
			main.sum = 1;
			int id = main.aren.getInt("sumo.number") + 1;
		            		            
		            main.aren.set("sumo.all." + id + ".player1.x" , player.getLocation().getBlockX());
		            main.aren.set("sumo.all." + id + ".player1.y" , player.getLocation().getBlockY());
					main.aren.set("sumo.all." + id + ".player1.z" , player.getLocation().getBlockZ());
					main.aren.set("sumo.all." + id + ".player1.yaw" , player.getLocation().getYaw());
					main.aren.set("sumo.all." + id + ".player1.pitch" , player.getLocation().getPitch());

										
					main.aren.set("sumo.all." + id + ".world" , player.getWorld().getName());

					main.aren.set("sumo.number" , id);


					main.aren.set("sumo.all." + id + ".player2.x" , 0);
					main.aren.set("sumo.all." + id + ".player2.y" , 0);
					main.aren.set("sumo.all." + id + ".player2.z" , 0);

					main.aren.set("sumo.all." + id + ".y" , 0);
					
					main.aren.set("sumo.all." + id + ".act" , false);
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe premier point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe premier point à eu un problème merci de contacter un administrateur");
						main.sum = 0;
					}
		        
            
		}else if(main.sum == 1) {
			main.sum = 2;
			int id = main.aren.getInt("sumo.number");
		            

					main.aren.set("sumo.all." + id + ".player2.x" , player.getLocation().getBlockX());
					main.aren.set("sumo.all." + id + ".player2.y" , player.getLocation().getBlockY());
					main.aren.set("sumo.all." + id + ".player2.z" , player.getLocation().getBlockZ());
					main.aren.set("sumo.all." + id + ".player2.yaw" , player.getLocation().getYaw());
					main.aren.set("sumo.all." + id + ".player2.pitch" , player.getLocation().getPitch());

		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe deuxième point à eu un problème merci de contacter un administrateur");
					main.sum = 0;
					}
					
		}else if(main.sum == 2) {
			main.sum = 0;
			int id = main.aren.getInt("sumo.number");
            
			main.aren.set("sumo.all." + id + ".y" , player.getLocation().getBlockY());


			main.aren.set("sumo.all." + id + ".act" , true);
            try {
				main.aren.save(main.getFile("arene"));
				player.sendMessage("§6§lLa descente s'est bien ajouter");
				main.inv.AnvilSumo(player, "" + id);
			} catch (IOException e1) {
				e1.printStackTrace();
				player.sendMessage("§6§lLa descente point à eu un problème merci de contacter un administrateur");
			main.sum = 0;
			}

		}
		
		return;
	}
	
	public void onAddParcour(Player player) {

		if(main.par == 0) {
			main.par = 1;
			int id = main.aren.getInt("parcour.number") + 1;
		            		            
		            main.aren.set("parcour.all." + id + ".player1.x" , player.getLocation().getBlockX());
		            main.aren.set("parcour.all." + id + ".player1.y" , player.getLocation().getBlockY());
					main.aren.set("parcour.all." + id + ".player1.z" , player.getLocation().getBlockZ());
					main.aren.set("parcour.all." + id + ".player1.yaw" , player.getLocation().getYaw());
					main.aren.set("parcour.all." + id + ".player1.pitch" , player.getLocation().getPitch());

										
					main.aren.set("parcour.all." + id + ".world" , player.getWorld().getName());

					main.aren.set("parcour.number" , id);


					main.aren.set("parcour.all." + id + ".player2.x" , 0);
					main.aren.set("parcour.all." + id + ".player2.y" , 0);
					main.aren.set("parcour.all." + id + ".player2.z" , 0);

					
					main.aren.set("parcour.all." + id + ".act" , false);
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe premier point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe premier point à eu un problème merci de contacter un administrateur");
						main.sum = 0;
					}
		        
            
		}else if(main.par == 1) {
			main.par = 2;
			int id = main.aren.getInt("parcour.number");
		            

					main.aren.set("parcour.all." + id + ".player2.x" , player.getLocation().getBlockX());
					main.aren.set("parcour.all." + id + ".player2.y" , player.getLocation().getBlockY());
					main.aren.set("parcour.all." + id + ".player2.z" , player.getLocation().getBlockZ());
					main.aren.set("parcour.all." + id + ".player2.yaw" , player.getLocation().getYaw());
					main.aren.set("parcour.all." + id + ".player2.pitch" , player.getLocation().getPitch());

		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe deuxième point à eu un problème merci de contacter un administrateur");
					main.sum = 0;
					}
					
		}else if(main.par == 2) {
			main.par = 3;
			int id = main.aren.getInt("parcour.number");
            
			main.aren.set("parcour.all." + id + ".end.y" , player.getLocation().getBlockY());
			main.aren.set("parcour.all." + id + ".end.point1.x" , player.getLocation().getBlockX());
			main.aren.set("parcour.all." + id + ".end.point1.z" , player.getLocation().getBlockZ());


            try {
				main.aren.save(main.getFile("arene"));
				player.sendMessage("§6§lLe premier point s'est bien ajouter");
			} catch (IOException e1) {
				e1.printStackTrace();
				player.sendMessage("§6§lLe premier point à eu un problème merci de contacter un administrateur");
			main.par = 0;
			}

		}else if(main.par == 3) {
			main.par = 0;
			int id = main.aren.getInt("parcour.number");
            
			main.aren.set("parcour.all." + id + ".end.y" , player.getLocation().getBlockY());
			main.aren.set("parcour.all." + id + ".end.point2.x" , player.getLocation().getBlockX());
			main.aren.set("parcour.all." + id + ".end.point2.z" , player.getLocation().getBlockZ());


			main.aren.set("parcour.all." + id + ".act" , true);
            try {
				main.aren.save(main.getFile("arene"));
				player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
				main.inv.AnvilParcour(player, "" + id);
			} catch (IOException e1) {
				e1.printStackTrace();
				player.sendMessage("§6§lLe deuxième point à eu un problème merci de contacter un administrateur");
			main.par = 0;
			}

		}
		
		return;
	}
	public void onAddFfa(Player player) {
		
		
		player.sendMessage("§6§l1");

		if(main.ff == 0) {
			player.sendMessage("§6§l2");
			main.ff = 1;
			int id = main.aren.getInt("ffa.number") + 1;
		            		            
		            main.aren.set("ffa.all." + id + ".point1.x" , player.getLocation().getBlockX());
		            main.aren.set("ffa.all." + id + ".point1.y" , player.getLocation().getBlockY());
					main.aren.set("ffa.all." + id + ".point1.z" , player.getLocation().getBlockZ());

										
					main.aren.set("ffa.all." + id + ".world" , player.getWorld().getName());

					main.aren.set("ffa.number" , id);


					main.aren.set("ffa.all." + id + ".point2.x" , 0);
					main.aren.set("ffa.all." + id + ".point2.y" , 0);
					main.aren.set("ffa.all." + id + ".point2.z" , 0);

					
					main.aren.set("ffa.all." + id + ".act" , false);
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe premier point s'est bien ajouter");
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe premier point à eu un problème merci de contacter un administrateur");
						main.ff = 0;
					}
		        
            
		}else if(main.ff == 1) {
			player.sendMessage("§6§l3");
			main.ff = 0;
			int id = main.aren.getInt("ffa.number");
		            

					main.aren.set("ffa.all." + id + ".point2.x" , player.getLocation().getBlockX());
					main.aren.set("ffa.all." + id + ".point2.y" , player.getLocation().getBlockY());
					main.aren.set("ffa.all." + id + ".point2.z" , player.getLocation().getBlockZ());

					main.aren.set("ffa.all." + id + ".act" , true);
					player.sendMessage("§6§l4");
		            try {
						main.aren.save(main.getFile("arene"));
						player.sendMessage("§6§lLe deuxième point s'est bien ajouter");
						main.inv.AnvilFfa(player, "" + id);
					} catch (IOException e1) {
						e1.printStackTrace();
						player.sendMessage("§c§lLe deuxième point à eu un problème merci de contacter un administrateur");
					main.ff = 0;
					}
					
		}
		
		return;
	}

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
		if(main.aren.getInt("arene.number") > 28*id) {
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
		if(main.aren.getInt("arene.number") < 28*id) {
			int base = id-1;
			int toto = 28*base;
			int obj = 0;
			while(nombre != main.aren.getInt("arene.number") - toto) {
				nombre++;
				obj = nombre +toto;
				if(main.aren.getBoolean("arene.all." + obj + ".act")) {
					it = new ItemStack(Material.WOOL, 1,(byte) 5);
				}
				else it = new ItemStack(Material.WOOL, 1,(byte) 14);
				
				ItemMeta ite = it.getItemMeta();
				ite.setDisplayName("§6§l" + main.aren.getString("arene.all." + obj + ".name"));
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
	
	public Inventory getDuel(Player player) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

		
		Inventory inv = Bukkit.createInventory(player, 27, "§6§lDemande en duel de " + main.duel.get(player).getName());
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack yes = new ItemStack(Material.WOOL, 1, (byte) 5);
		ItemMeta yes2 = yes.getItemMeta();
		yes2.setDisplayName("§a§lAccepter");
		yes.setItemMeta(yes2);
		
		ItemStack no = new ItemStack(Material.WOOL, 1, (byte) 14);
		ItemMeta no2 = no.getItemMeta();
		no2.setDisplayName("§c§lRefuser");
		no.setItemMeta(no2);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1 , (byte) 3);
		SkullMeta sk = (SkullMeta) skull.getItemMeta();
		sk.setDisplayName("§6§l" + main.duel.get(player).getName());
		sk.setOwner(main.duel.get(player).getName());
		skull.setItemMeta(sk);
		
		ItemStack it = kite.getItemStack("kit." + main.duelkit.get(player) + ".item");
		ItemMeta ite = it.getItemMeta();
		ite.setDisplayName("§6§l" + kite.getString("kit." + main.duelkit.get(player) + ".name"));
		ite.setLore(null);
		it.setItemMeta(ite);
		
		inv.setItem(0, gpvert);
		inv.setItem(8, gpvert);
		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);
		inv.setItem(18, gpvert);
		inv.setItem(26, gpvert);

		inv.setItem(4, skull);
		inv.setItem(11, yes);
		inv.setItem(15, no);
		inv.setItem(22, it);


		return inv;
	}
	public Inventory isDuel(Player player, Player deman) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);


		if(main.kit.size() <= 5) {
			inv = Bukkit.createInventory(player, 45, "§6§lDemande en duel à " + deman.getName());
		}else if(main.kit.size() >= 6) {
			inv = Bukkit.createInventory(player, 54, "§6§lDemande en duel à " + deman.getName());
		}
		int nb = 0;
		int numb = 0;
		ItemStack kit = null;
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta sk = (SkullMeta) skull.getItemMeta();
		sk.setDisplayName("§6§l" + deman.getName());
		sk.setOwner(deman.getName());
		skull.setItemMeta(sk);
		
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if(kite.getBoolean("kit." + nb + ".act") == true) {
				
				
				kit = kite.getItemStack("kit." + nb + ".item");
				ItemMeta kit2 = kit.getItemMeta();
				String stre = kite.getString("kit." + nb + ".name");
				kit2.setDisplayName("§6§l" + stre);
				kit2.setLore(null);
				kit2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
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

		inv.setItem(4, skull);
		
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
	
	public Inventory setProperties(Player player, String id) {
		Inventory inv = Bukkit.createInventory(player, 45, "§6§lOption " + id);

		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);

		ItemStack sumo = new ItemStack(Material.SLIME_BALL, 1);
		ItemMeta slim = sumo.getItemMeta();
		slim.setDisplayName("§a§lSumo");
		if(kite.getBoolean("kit." + id + ".prop.sumo")) {
			slim.addEnchant(Enchantment.DURABILITY, 1, true);
		}else {
			slim.removeEnchant(Enchantment.DURABILITY);
		}	
		sumo.setItemMeta(slim);
		
		
		
		ItemStack par = new ItemStack(Material.LADDER, 1);
		ItemMeta ladder = par.getItemMeta();
		ladder.setDisplayName("§6§lParcour");
		if(kite.getBoolean("kit." + id + ".prop.parcour")) {
			ladder.addEnchant(Enchantment.DURABILITY, 1, true);
		}else {
			ladder.removeEnchant(Enchantment.DURABILITY);
		}	
		par.setItemMeta(ladder);
		
		
		ItemStack rien = new ItemStack(Material.DEAD_BUSH, 1);
		ItemMeta no = par.getItemMeta();
		no.setDisplayName("§c§lDesactiver");
		if((!(kite.getBoolean("kit." + id + ".prop.parcour"))) && (!(kite.getBoolean("kit." + id + ".prop.sumo")))) {
			no.addEnchant(Enchantment.DURABILITY, 1, true);
		}else {
			no.removeEnchant(Enchantment.DURABILITY);
		}	
		rien.setItemMeta(no);

		
		
		
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);
		
		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);
		
		inv.setItem(20, sumo);
		inv.setItem(22, rien);
		inv.setItem(24, par);

		
		inv.setItem(27, gpvert);
		inv.setItem(35, gpvert);

		inv.setItem(36, gpvert);
		inv.setItem(37, gpvert);
		inv.setItem(38, gpvert);

		inv.setItem(42, gpvert);
		inv.setItem(43, gpvert);
		inv.setItem(44, gpvert);
		return inv;

	}
	
	

	
}
