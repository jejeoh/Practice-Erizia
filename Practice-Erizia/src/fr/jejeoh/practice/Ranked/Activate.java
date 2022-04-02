package fr.jejeoh.practice.Ranked;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;
import fr.jejeoh.practice.DB.DBConnect;

public class Activate {
	
	private Base main;
	
	public Activate(Base main) {
		this.main = main;
	}
	
	public void Activ(String id, Player player) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));
		if(kite.getBoolean("kit." + id + ".ranked") == true) {
				kite.set("kit." + id + ".ranked" , false);
	            try {
					kite.save(main.getFile("kit"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

		        main.getConnect().actKit(true, id, false);

			main.rankit.remove("" + id);
			main.rankitingame.remove("" + id);
			
			player.sendMessage("§6§lLe ranked du kit a été désactivé !");
			

			
		}else if(kite.getBoolean("kit." + id + ".ranked") == false) {
				kite.set("kit." + id + ".ranked" , true);
	            try {
					kite.save(main.getFile("kit"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
            main.getConnect();

	        try {
				PreparedStatement preparedStatement = DBConnect.connection.prepareStatement("SELECT * FROM users WHERE elo_"+ id);
	            preparedStatement.executeQuery();
	        } catch (SQLException e) {
            	PreparedStatement pr;
				try {
					pr = DBConnect.connection.prepareStatement("ALTER TABLE users ADD elo_" + id + " int DEFAULT 700");
					pr.execute();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				main.getConnect().Kitadd(id, true, kite.getString("kit." + id + ".name"));
	        }

	        main.getConnect().actKit(true, id, true);
	        
			List<Player> pl = new ArrayList<>();
			main.rankit.put("" + id, pl);
			List<Player> plu = new ArrayList<>();
			main.rankitingame.put("" + id, plu);
			
			player.sendMessage("§6§lLe ranked du kit a été activé !");
			
			
			
			
		}
	}	
		public void Clear(String id, Player player) {

        main.getConnect();

        try {
			PreparedStatement preparedStatement = DBConnect.connection.prepareStatement("SELECT * FROM users WHERE elo_"+ id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
        	player.sendMessage("§c§lActiver déjà une fois le mode ranked sur ce kit !");
        	return;
        }
    	PreparedStatement pr;
		try {
			pr = DBConnect.connection.prepareStatement("ALTER TABLE users DROP elo_" + id);
			pr.execute();
			pr = DBConnect.connection.prepareStatement("ALTER TABLE users ADD elo_" + id + " int DEFAULT 700");
			pr.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

        
	}


}
