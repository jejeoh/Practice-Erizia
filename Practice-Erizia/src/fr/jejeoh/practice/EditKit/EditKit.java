package fr.jejeoh.practice.EditKit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.jejeoh.practice.Base;

public class EditKit {
	
	private Base main;
	
	public Map<Player, String> kitedit = new HashMap<>();
	
	public EditKit(Base main) {
		
		this.main = main;

		main.creatFile("internDB");
		main.creatFile("arene");
		main.creatFile("kit");

		FileConfiguration internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
		FileConfiguration arene = YamlConfiguration.loadConfiguration(main.getFile("arene"));
		FileConfiguration kit = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		try {
			internDB.save(main.getFile("internDB"));
			arene.save(main.getFile("arene"));
			kit.save(main.getFile("kit"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		

	
		
		
		
	}
	
	public ItemStack Item() {
		ItemStack edkit = new ItemStack(Material.BOOK);
		ItemMeta editkit = edkit.getItemMeta();
		editkit.setDisplayName("§6§lEditer un kit");
		edkit.setItemMeta(editkit);
		return edkit;
	}
	
	public Inventory Inv(Player player) {
		Inventory inv = null;
	
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		
		if(main.kit.size() <= 7) {
			inv = Bukkit.createInventory(player, 27, "§6§lModifier un kit");
			inv.setItem(18,gpvert);
			inv.setItem(19,gpvert);
			inv.setItem(20,gpvert);
			inv.setItem(21,gpvert);
			inv.setItem(22,gpvert);
			inv.setItem(23,gpvert);
			inv.setItem(24,gpvert);
			inv.setItem(25,gpvert);
			inv.setItem(26,gpvert);
		}else if(main.kit.size() <= 14) {
			inv = Bukkit.createInventory(player, 36, "§6§lModifier un kit");
			inv.setItem(18,gpvert);
			inv.setItem(26,gpvert);
			inv.setItem(27,gpvert);
			inv.setItem(28,gpvert);
			inv.setItem(29,gpvert);
			inv.setItem(30,gpvert);
			inv.setItem(31,gpvert);
			inv.setItem(32,gpvert);
			inv.setItem(33,gpvert);
			inv.setItem(34,gpvert);
			inv.setItem(35,gpvert);
		}else if(main.kit.size() <= 21) {
			inv = Bukkit.createInventory(player, 45, "§6§lModifier un kit");
			inv.setItem(18,gpvert);
			inv.setItem(26,gpvert);
			inv.setItem(27,gpvert);
			inv.setItem(35,gpvert);
			inv.setItem(36,gpvert);
			inv.setItem(37,gpvert);
			inv.setItem(38,gpvert);
			inv.setItem(39,gpvert);
			inv.setItem(40,gpvert);
			inv.setItem(41,gpvert);
			inv.setItem(42,gpvert);
			inv.setItem(43,gpvert);
			inv.setItem(44,gpvert);
		}else{
			inv = Bukkit.createInventory(player, 54, "§6§lModifier un kit");
			inv.setItem(18,gpvert);
			inv.setItem(26,gpvert);
			inv.setItem(27,gpvert);
			inv.setItem(35,gpvert);
			inv.setItem(36,gpvert);
			inv.setItem(44,gpvert);
			inv.setItem(45,gpvert);
			inv.setItem(46,gpvert);
			inv.setItem(47,gpvert);
			inv.setItem(48,gpvert);
			inv.setItem(49,gpvert);
			inv.setItem(50,gpvert);
			inv.setItem(51,gpvert);
			inv.setItem(52,gpvert);
			inv.setItem(53,gpvert);
			inv.setItem(54,gpvert);
		}
		
		inv.setItem(0,gpvert);
		inv.setItem(1,gpvert);
		inv.setItem(2,gpvert);
		inv.setItem(3,gpvert);
		inv.setItem(4,gpvert);
		inv.setItem(5,gpvert);
		inv.setItem(6,gpvert);
		inv.setItem(7,gpvert);
		inv.setItem(8,gpvert);
		inv.setItem(9,gpvert);
		inv.setItem(17,gpvert);
		
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
				kit2.removeEnchant(Enchantment.DURABILITY);
				kit.setItemMeta(kit2);
				numb++;
				if( numb <= 7) {
					inv.setItem(9+numb, kit);
				}else if( numb >= 14){
					inv.setItem(18+numb, kit);
				}else if( numb >= 21){
					inv.setItem(27+numb, kit);
				}else {
					inv.setItem(36+numb, kit);
				}
				
			}
		}
		
		
		
		
		return inv;
	}
	
	public void onChange(Player player, String kit) {
		player.teleport(new Location(Bukkit.getWorld(main.getInstance().getConfig().getString("editkit.world")),main.getInstance().getConfig().getInt("editkit.x"), main.getInstance().getConfig().getInt("editkit.y"), main.getInstance().getConfig().getInt("editkit.z") ));
		kitedit.put(player, kit);
		
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
	}
		
		

	
	
	
	
	
	
	
	public void isChange(Player player) {
		
		File file = main.getFile("internDB");
		
		FileConfiguration internDB = null;
		
		String id = kitedit.get(player);

		int numb = 0;
		int nomb2 = 0;
		while(numb != 36) {
			nomb2 = numb + 1;
			internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
	        internDB.set(player.getUniqueId() + "." + id + ".inventory." + "" + nomb2, player.getInventory().getItem(numb));
			try {
				internDB.save(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			numb++;
		}
		internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));

        internDB.set(player.getUniqueId() + "." + id + ".inventory.helmet", player.getInventory().getHelmet());
		try {
			internDB.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));

        internDB.set(player.getUniqueId() + "." + id + ".inventory.chestplate", player.getInventory().getChestplate());
		try {
			internDB.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));
        
            internDB.set(player.getUniqueId() + "." + id + ".inventory.leggings", player.getInventory().getLeggings());
            try {
    			internDB.save(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));

        internDB.set(player.getUniqueId() + "." + id + ".inventory.boots", player.getInventory().getBoots());
		try {
			internDB.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		internDB = YamlConfiguration.loadConfiguration(main.getFile("internDB"));

        internDB.set(player.getUniqueId() + "." + id + ".act", true);
		try {
			internDB.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
		player.teleport(loc);
		
		kitedit.remove(player);
		
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
		player.getInventory().setItem(4, Item());

		player.updateInventory();


		return;
	}
	
}
