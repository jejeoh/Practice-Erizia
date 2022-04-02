package fr.jejeoh.practice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.jejeoh.practice.API.EndMessage;
import fr.jejeoh.practice.API.Title;
import fr.jejeoh.practice.Arene.AreneManager;
import fr.jejeoh.practice.Arene.AreneManager.Arene;
import fr.jejeoh.practice.Arene.Properties.Parcour;
import fr.jejeoh.practice.Arene.Properties.Sumo;
import fr.jejeoh.practice.Commandes.CommandKit;
import fr.jejeoh.practice.Commandes.CommandeDuel;
import fr.jejeoh.practice.Commandes.Duel;
import fr.jejeoh.practice.DB.DBConnect;
import fr.jejeoh.practice.EditKit.EditKit;
import fr.jejeoh.practice.Groupe.Groupe;
import fr.jejeoh.practice.Listenaire.EventDeath;
import fr.jejeoh.practice.Listenaire.EventGame;
import fr.jejeoh.practice.Ranked.ManagerRanked;

public class Base extends JavaPlugin {
	
	private static Base instance;
	
	
	public List<Player> online = new ArrayList<>();
	public List<Player> ranonline = new ArrayList<>();
	public List<Player> sumoonline = new ArrayList<>();
	public List<Player> parcouronline = new ArrayList<>();
	public List<Player> att = new ArrayList<>();
	public List<Arene> arenenjeu = new ArrayList<>();
	public List<Sumo> sumosansjeu = new ArrayList<>();
	public List<Sumo> sumoenjeu = new ArrayList<>();
	public List<Parcour> parcoursansjeu = new ArrayList<>();
	public List<Parcour> parcourenjeu = new ArrayList<>();
	public List<Arene> arensansjeu = new ArrayList<>();

	public List<NumberTimer> timerstart = new ArrayList<>();
	public List<NumberTimer> timeren = new ArrayList<>();

	public Map<NumberTimer, Player> timerst = new HashMap<>();
	public Map<NumberTimer, Player> timerend = new HashMap<>();
	public Map<Player, Arene> end = new HashMap<>();
	public Map<Player, Sumo> sumoend = new HashMap<>();
	public Map<Player, String> sumoget = new HashMap<>();
	public Map<Player, Player> game = new HashMap<>();
	public Map<Player, Player> duel = new HashMap<>();
	public Map<Player, String> duelkit = new HashMap<>();
	public Map<Player, String> sumoar = new HashMap<>();
	public Map<Player, String> parcourar = new HashMap<>();
	public Map<Player, String> play = new HashMap<>();
	public Map<Player, String> ranplay = new HashMap<>();
	public Map<String, List<Player>> kit = new HashMap<>();
	public Map<String, List<Player>> kitingame = new HashMap<>();
	public Map<String, List<Player>> rankit = new HashMap<>();
	public Map<String, List<Player>> rankitingame = new HashMap<>();
	public Map<Player, List<Block>> bc = new HashMap<>();

	public Map<Player, Parcour> parcourend = new HashMap<>();
	public Map<Player, String> parcourget = new HashMap<>();
	
	public DBConnect db;
	public InventoryManager inv;
	public ManagerRanked ran;
	public EndMessage mess;
	public Groupe groupe;
	public AreneManager arene;
	public Duel duele;
	public EditKit ed;
	public Title title = new Title();
	Scoreboard score;
	
	public FileConfiguration aren  = YamlConfiguration.loadConfiguration(getFile("arene"));
	
	public String id = "0";

	public int getar = 0;
	public int etat = 0;
	public int sum = 0;
	public int par = 0;
	public int toid = 0;
	public int ff = 0;

	public File configurationFile = new File(getDataFolder(), "config.yml");
	
	
	
	@Override
	public void onEnable() {
		System.out.println("Sa lance le serveur");
		saveDefaultConfig();
		System.out.println("la config marche");
		instance = this;
		System.out.println("instance passe est this");
		
		db = new DBConnect("jdbc:mysql://", getConfig().getString("database.niveau.host"), getConfig().getString("database.niveau.dbname") +  "?autoReconnect=true", getConfig().getString("database.niveau.user"), getConfig().getString("database.niveau.password"), this);
		System.out.println("ajt les config à la db");
		inv = new InventoryManager(this);
		System.out.println("start de l'inventoryManager");
		arene = new AreneManager(this);
		mess = new EndMessage();
		ed = new EditKit(this);
		groupe = new Groupe(this);
		System.out.println("start de des arenes");
		db.connexion();
		System.out.println("Connection DB");
 		score = new Scoreboard(this);
 		duele = new Duel(this);
		score.runTaskTimer(this, 0, 20);
 		System.out.println("Start boucle");
 		ran = new ManagerRanked(this);
		System.out.println("Démarrage du ranked");
		System.out.println("§c######################################");
        System.out.println("§c#                                    §c#");
        System.out.println("§c#         §1Practice by Erizia         §c#");
        System.out.println("§c#                                    §c#");
        System.out.println("§c#         §dDémarrage en cours         §c#");
        System.out.println("§c#                                    #");
        if(db.isOnline()) System.out.println("§c#         §1DataBase SQL : §aV         §c#");
        else System.out.println("§c#         §1DataBase SQL : §4X         §c#");
        System.out.println("§c#                                    §c#");
        System.out.println("§c######################################");
        
		
	    getServer().getPluginManager().registerEvents(new EventGame(this), this);
	    getServer().getPluginManager().registerEvents(new EventDeath(this), this);
	    
	    
	    
	    getCommand("kiteditor").setExecutor(new CommandKit(this));
	    getCommand("test").setExecutor(new CommandKit(this));
	    getCommand("setarene").setExecutor(new CommandKit(this));
	    getCommand("getarene").setExecutor(new CommandKit(this));
	    getCommand("sword").setExecutor(new CommandKit(this));
	    getCommand("ping").setExecutor(new CommandKit(this));
	    getCommand("update").setExecutor(new CommandKit(this));
	    getCommand("elo").setExecutor(new CommandKit(this));
	    getCommand("debug").setExecutor(new CommandKit(this));
	    getCommand("stat").setExecutor(new CommandKit(this));
	    getCommand("statdef").setExecutor(new CommandKit(this));
	    getCommand("duel").setExecutor(new CommandeDuel(this));
	    getCommand("setsumo").setExecutor(new CommandKit(this));
	    getCommand("setparcour").setExecutor(new CommandKit(this));
	    getCommand("setffa").setExecutor(new CommandKit(this));

	    
	}
	
	@Override
	public void onDisable() {
		db.deconnexion();
	}

	public DBConnect getConnect(){
		return db;
	}
	
	public Base getInstance() {
		return instance;
	}
	
	public static Base getIn() {
		return instance;
	}
	
	public void creatFile(String fileName) {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), fileName + ".yml");

		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public File getFile(String fileName) {
		return new File(getDataFolder(), fileName + ".yml");
	}
	public void onGive(Player player) {
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		
		if(groupe.getGrp.containsKey(player)) {
			if(groupe.chef.containsValue(player)) {
				groupe.onGive(player);
				player.updateInventory();
			}else {
				groupe.onSous(player);
				player.updateInventory();
			}
		}else {
			
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
			player.getInventory().setItem(4, ed.Item());
			
			player.updateInventory();			
			
		}
	}
	
	
}
