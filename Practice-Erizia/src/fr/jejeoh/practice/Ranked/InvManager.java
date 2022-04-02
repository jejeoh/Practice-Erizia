package fr.jejeoh.practice.Ranked;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.jejeoh.practice.Base;

public class InvManager {
	
	private Base main;
	
	public InvManager(Base main) {
		this.main = main;
	}
	
	public Inventory EditRan(String nb, Player player) {
		
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);

		ItemStack no = null;	
		
		if(kite.getBoolean("kit." + nb + ".ranked") == true) {
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
		
		ItemStack cl = new ItemStack(Material.HOPPER, 1);
		ItemMeta cl2 = cl.getItemMeta();
		cl2.setDisplayName("§7§lClear");
		cl.setItemMeta(cl2);	
		
		ItemStack back = new ItemStack(Material.BARRIER, 1);
		ItemMeta back2 = back.getItemMeta();
		back2.setDisplayName("§2§lBack");
		back.setItemMeta(back2);		
		
		inv = Bukkit.createInventory(player, 45, "§6§lRanked Kit " +  nb);
		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);

		
		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);

		inv.setItem(20, no);
		inv.setItem(24, cl);

		
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
	public Inventory OpenRan(Player player) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		Inventory inv = null;
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		List<String> li = new ArrayList<>();
		li.add("§cElo : " + main.getConnect().getElo(player.getName(), "0"));
		li.add("");
		int nb = 0;
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if(kite.getBoolean("kit." + nb + ".ranked")) {
				li.add("§a§l" + kite.getString("kit." + nb + ".name") + " : " + main.getConnect().getElo(player.getName(), "" + nb));
			}
		}
		
		ItemStack pla = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta pla2 = (SkullMeta) pla.getItemMeta();
		pla2.setDisplayName("§6§l" + player.getName());
		pla2.setLore(li);
		pla2.setOwner(player.getName());
		pla.setItemMeta(pla2);


		int nob = 0;
		int numb = 0;
		ItemStack kit = null;
		if(kite.getInt("kitnumber") <= 5) {
			inv = Bukkit.createInventory(player, 45, "§c§lMode Ranked");
		}else if(kite.getInt("kitnumber") >= 6) {
			inv = Bukkit.createInventory(player, 54, "§c§lMode Ranked");
		}
		while(nob != kite.getInt("kitnumber")) {
			nob++;
			if(kite.getBoolean("kit." + nob + ".act") == true && kite.getBoolean("kit." + nob + ".ranked") == true ) {
				
				List<Player> ingame = main.rankitingame.get("" + nob);
				List<Player> game = main.rankit.get("" + nob);
				
				kit = kite.getItemStack("kit." + nob + ".item");
				ItemMeta kit2 = kit.getItemMeta();
				String stre = kite.getString("kit." + nob + ".name");
				kit2.setDisplayName("§6§l" + stre);
				kit2.setLore(Arrays.asList("§cJoueur en combat : " + ingame.size(), "§aJoueur en liste d'attente : " + game.size(), "", "§6Ton Elo : " + main.getConnect().getElo(player.getName(), "" + nob)));
				kit2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				if(game.contains(player)) {
					kit2.addEnchant(Enchantment.DURABILITY, 1, true);
				}else {
					kit2.removeEnchant(Enchantment.DURABILITY);
				}
				kit.setItemMeta(kit2);
				numb++;
				if( numb <= 5) {
					inv.setItem(19+numb, kit);
				}else if( numb >= 6){
					inv.setItem(28+numb, kit);
				}
				
			}
		}
		
		
		

		
		inv.setItem(0, gpvert);
		inv.setItem(1, gpvert);
		inv.setItem(2, gpvert);
		
		inv.setItem(4, pla);

		inv.setItem(6, gpvert);
		inv.setItem(7, gpvert);
		inv.setItem(8, gpvert);

		inv.setItem(9, gpvert);
		inv.setItem(17, gpvert);

		if( numb <= 5) {

			
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
	
	
}
