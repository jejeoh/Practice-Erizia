package fr.jejeoh.practice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.jejeoh.practice.Arene.AreneManager;
import fr.jejeoh.practice.Arene.AreneManager.Arene;
import fr.jejeoh.practice.Arene.AreneManager.Int;
import fr.jejeoh.practice.DB.DBConnect;
import fr.jejeoh.practice.Listenaire.EventDeath;
import fr.jejeoh.practice.Listenaire.EventGame;

public class Base extends JavaPlugin {
	
	private static Base instance;
	
	
	public List<Player> online = new ArrayList<>();
	public List<Player> att = new ArrayList<>();
	public List<Arene> arenenjeu = new ArrayList<>();
	public List<Arene> arensansjeu = new ArrayList<>();
	public List<Int> timerstart = new ArrayList<>();

	public Map<Int, Player> timerst = new HashMap<>();
	public Map<Player, Arene> end = new HashMap<>();
	public Map<Player, Player> game = new HashMap<>();
	public Map<Player, String> play = new HashMap<>();
	public Map<String, List<Player>> kit = new HashMap<>();
	public Map<String, List<Player>> kitingame = new HashMap<>();
	public Map<Player, List<Block>> bc = new HashMap<>();


	private DBConnect dbconnect;
	public InventoryManager inv;
	public AreneManager arene;
	Scoreboard score;
	
	public String id = "0";
	public int getar = 0;
	public int etat = 0;
	
	public File configurationFile = new File(getDataFolder(), "config.yml");
	
	
	
	@Override
	public void onEnable() {
		System.out.println("Sa lance le serveur");
		saveDefaultConfig();
		System.out.println("la config marche");
		instance = this;
		System.out.println("instance passe est this");
		
		dbconnect = new DBConnect("jdbc:mysql://", getConfig().getString("database.niveau.host"), getConfig().getString("database.niveau.dbname") +  "?autoReconnect=true", getConfig().getString("database.niveau.user"), getConfig().getString("database.niveau.password"));
		System.out.println("ajt les config à la db");
		inv = new InventoryManager(this);
		System.out.println("start de l'inventoryManager");
		arene = new AreneManager(this);
		System.out.println("start de l'inventoryManager");
 		//dbconnect.connexion();
		score = new Scoreboard(this);
		score.runTaskTimer(this, 0, 20);
 		
		System.out.println("Démarrage de la db");
		System.out.println("§c######################################");
        System.out.println("§c#                                    §c#");
        System.out.println("§c#         §1Practice by Erizia         §c#");
        System.out.println("§c#                                    §c#");
        System.out.println("§c#         §dDémarrage en cours         §c#");
        System.out.println("§c#                                    #");
        if(dbconnect.isOnline()) System.out.println("§c#         §1DataBase SQL : §aV         §c#");
        else System.out.println("§c#         §1DataBase SQL : §4X         §c#");
        System.out.println("§c#                                    §c#");
        System.out.println("§c######################################");
        
		
	    getServer().getPluginManager().registerEvents(new EventGame(this), this);
	    getServer().getPluginManager().registerEvents(new EventDeath(this), this);
	    
	    
	    
	    getCommand("kiteditor").setExecutor(new CommandKit(this));
	    getCommand("test").setExecutor(new CommandKit(this));
	    getCommand("setarene").setExecutor(new CommandKit(this));
	    getCommand("getarene").setExecutor(new CommandKit(this));
	    
	    
	    
	    
	}
	
	@Override
	public void onDisable() {
		dbconnect.deconnexion();
	}

	public DBConnect getConnect(){
		return dbconnect;
	}
	
	public Base getInstance() {
		return instance;
	}
	
	public static Base getIn() {
		return instance;
	}
	
	
}
