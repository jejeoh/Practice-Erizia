package fr.jejeoh.practice.Ranked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;

public class ManagerRanked {
	
	public Map<String, List<Player>> kit = new HashMap<>();
	public Map<String, List<Player>> kitingame = new HashMap<>();

	private Base main;
	
	public InvManager inv;
	public Activate act;
	
	
	public ManagerRanked(Base main) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		inv = new InvManager(main);
		act = new Activate(main);
		this.main = main;
		
		int nb = 0;
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if(kite.getBoolean("kit." + nb + ".act") == true) {
				List<Player> pl = new ArrayList<>();
				main.kit.put("" + nb, pl);
				List<Player> plu = new ArrayList<>();
				main.kitingame.put("" + nb, plu);
			}			
		}
	}
	
	public int getElo(Player gagnant, Player perdant, String kit) {
		int elo = 0;
		int dif = main.getConnect().getElo(gagnant.getName(), kit) - main.getConnect().getElo(perdant.getName(), kit);
		if(dif >= 800) elo = 3;
		else if(dif >= 500) elo = 4;
		else if(dif >= 300) elo = 5;
		else if(dif >= 200) elo = 6;
		else if(dif >= 100) elo = 7;
		else if(dif >= 0) elo = 8;
		else if(dif >= -100) elo = 9;
		else if(dif >= -200) elo = 10;
		else if(dif >= -300) elo = 11;
		else if(dif >= -500) elo = 12;
		else if(dif >= -800) elo = 13;
		else elo = 14;
		return elo;
	}

}
