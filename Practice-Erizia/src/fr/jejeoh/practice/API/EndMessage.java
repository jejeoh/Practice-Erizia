package fr.jejeoh.practice.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class EndMessage {
	
	
	public int id = 0;
	public Map<String, Inventory> getinv = new HashMap<>();
	public Map<String, Inventory> getinv2 = new HashMap<>();
	public Map<String, List<String>> stat = new HashMap<>();

	public EndMessage() {
		
	}
	
	
	
	public void Message(Player gagnant, Player perdant) {
		
		List<String> li = new ArrayList<>();
		
		li.add("§a§l" + gagnant.getName() + " : " + gagnant.getHealth() + " §c§l♥");
		li.add("§c§l" + perdant.getName() + " : 0 §c§l♥");

		stat.put("" + id, li);
		
		ItemStack gpvert = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta gvert = gpvert.getItemMeta();
		gvert.setDisplayName(" ");
		gpvert.setItemMeta(gvert);
		
		ItemStack back = new ItemStack(Material.BARRIER, 1);
		ItemMeta bc = back.getItemMeta();
		bc.setDisplayName("§c§lClose");
		back.setItemMeta(bc);		
		
		ItemStack plus = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta pl = (SkullMeta) plus.getItemMeta();
		pl.setOwner("MHF_Question");
		pl.setDisplayName("§a§lPlus de Statistic");
		plus.setItemMeta(pl);
		
		ItemStack play = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta pla = (SkullMeta) play.getItemMeta();
		pla.setOwner(perdant.getName());
		pla.setDisplayName("§a§l" + perdant.getName());
		play.setItemMeta(pla);
		
		Inventory inv = Bukkit.createInventory(null, 54, "§6§lInventory gagnant " + id);
		
		inv.setItem(13, gpvert);
		inv.setItem(14, gpvert);
		inv.setItem(15, gpvert);
		inv.setItem(16, gpvert);
		inv.setItem(17, gpvert);
		
		inv.setItem(45, back);
		inv.setItem(46, gpvert);
		inv.setItem(47, gpvert);
		inv.setItem(48, gpvert);
		inv.setItem(49, plus);
		inv.setItem(50, gpvert);
		inv.setItem(51, gpvert);
		inv.setItem(52, gpvert);
		inv.setItem(53, play);

		int nb = 0;
		
		while(nb != 9) {
			inv.setItem(nb, gagnant.getInventory().getItem(nb));
			nb++;
		}
		while(nb != 36) {
			inv.setItem(nb+9, gagnant.getInventory().getItem(nb));
			nb++;
		}
		
		inv.setItem(9, gagnant.getInventory().getHelmet());
		inv.setItem(10, gagnant.getInventory().getChestplate());
		inv.setItem(11, gagnant.getInventory().getLeggings());
		inv.setItem(12, gagnant.getInventory().getBoots());

		
		getinv.put("" + id ,inv);
		
		ItemStack plat = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta plate = (SkullMeta) plat.getItemMeta();
		plate.setOwner(gagnant.getName());
		plate.setDisplayName("§a§l" + gagnant.getName());
		plat.setItemMeta(plate);
		
		Inventory inve = Bukkit.createInventory(null, 54, "§6§lInventory perdant " + id);
		
		inve.setItem(13, gpvert);
		inve.setItem(14, gpvert);
		inve.setItem(15, gpvert);
		inve.setItem(16, gpvert);
		inve.setItem(17, gpvert);
		
		inve.setItem(45, back);
		inve.setItem(46, gpvert);
		inve.setItem(47, gpvert);
		inve.setItem(48, gpvert);
		inve.setItem(49, plus);
		inve.setItem(50, gpvert);
		inve.setItem(51, gpvert);
		inve.setItem(52, gpvert);
		inve.setItem(53, plat);

		nb = 0;
		
		while(nb != 9) {
			inve.setItem(nb, perdant.getInventory().getItem(nb));
			nb++;
		}
		while(nb != 36) {
			inve.setItem(nb+9, perdant.getInventory().getItem(nb));
			nb++;
		}
		
		inve.setItem(9, perdant.getInventory().getHelmet());
		inve.setItem(10, perdant.getInventory().getChestplate());
		inve.setItem(11, perdant.getInventory().getLeggings());
		inve.setItem(12, perdant.getInventory().getBoots());

		
		getinv2.put("" + id ,inve);
		
		
		TextComponent win = new TextComponent("§a§lGagnant : " + gagnant.getName());
		win.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Voir l' inventaires").create()));
		win.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stat " + id));
		
		TextComponent lose = new TextComponent("§c§lPerdant : " + perdant.getName());
		lose.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Voir l' inventaires").create()));
		lose.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/statdef " + id));
		
		gagnant.sendMessage("§6§lMatch Fini §r§7(Click sur le joueur)");
		perdant.sendMessage("§6§lMatch Fini §r§7(Click sur le joueur)");
		gagnant.spigot().sendMessage(win);
		perdant.spigot().sendMessage(win);
		gagnant.spigot().sendMessage(lose);
		perdant.spigot().sendMessage(lose);
		id++;
		
		return;
	}

}
