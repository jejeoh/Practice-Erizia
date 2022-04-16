package fr.jejeoh.practice.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.jejeoh.practice.Base;

public class DBConnect {
	
    private String urlBase;
    private String host;
    private String database;
    private String username;
    private String password;
    
	private Base main;
	
	public Map<String, String> getelo = new HashMap<>();


    public static Connection connection;

    public DBConnect(String urlBase, String host, String database, String username, String password, Base main) {
		this.main = main;
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        }
 public static Connection getConnection() {
        return connection;

    }
 public void createAccount(Player p) {
	 

        if (!hasAccont(p)) {
    		main.getInstance().title.sendTitle(p, 10, 60, 10, "§6Bienvenue");
            try  {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (uuid, player_name) VALUES (?,?)");
                preparedStatement.setString(1, p.getUniqueId().toString());
                preparedStatement.setString(2, p.getName().toString());
                preparedStatement.execute();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }


            
        }

    }


    public boolean hasAccont(Player p) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM users WHERE uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.close();

            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 public void connexion() {
        if (!isOnline()) {
            try {
                connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.username, this.password);
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        if (isOnline()) {
            try {
                connection.close();
                System.out.println("§c[Practice] Successfully disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isOnline() {
        try {
            if ((connection == null) || (connection.isClosed())) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getElo(String player , String kit) {
        PreparedStatement preparedStatement;
        ResultSet rs = null;
		int in = 180;
		if(getelo.get(player + "_" + kit) == null) {
			try {
				preparedStatement = connection.prepareStatement("SELECT elo FROM stat WHERE player_name ='" + player +"' AND kit_id=" + kit);
		        rs = preparedStatement.executeQuery();
                preparedStatement.close();
		        while(rs.next()) {
		        	if(rs.getInt("elo") == 0) break;
			        getelo.put(player + "_" + kit, rs.getInt("elo") + "");
		        	return rs.getInt("elo");
		        }

		
		        
		        
			} catch (SQLException e) {

			}
			
	        try {
		        preparedStatement = connection.prepareStatement("INSERT INTO stat (`player_name`, `kit_id`, `elo`) VALUES ('" + player +"', '" + kit +"', '700')");
				preparedStatement.execute();
		        preparedStatement.close();
		        getelo.put(player + "_" + kit,"700");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			
		}
		in = Integer.parseInt(getelo.get(player + "_" + kit));

		return in;
    }
    public void addElo(String player, String kit, int elo) {
    	int nb = getElo(player, kit)+elo;
        PreparedStatement preparedStatement;
		try  {
			preparedStatement = connection.prepareStatement("UPDATE stat SET elo=" + nb +" WHERE player_name = ? AND kit_id= ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.setString(2, kit);
	        preparedStatement.execute();
            preparedStatement.close();

	        getelo.remove(player + "_" + kit);
	        getelo.put(player + "_" + kit,  nb+ "");

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    public void removeElo(String player, String kit, int elo) {
    	int nb = getElo(player, kit)-elo;
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE stat SET elo=" + nb +" WHERE player_name = ? AND kit_id= ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.setString(2, kit);
	        preparedStatement.execute();
	        getelo.remove(player + "_" + kit);
	        getelo.put(player + "_" + kit,  nb+ "");
            preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    public void setElo(String player, String kit, int elo) {
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE stat SET elo=" + elo +" WHERE player_name = ? AND kit_id= ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.setString(2, kit);
	        preparedStatement.execute();
	        getelo.remove(player + "_" + kit);
	        getelo.put(player + "_" + kit,  elo+ "");
            preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    public void updateElo(String player) {
		FileConfiguration kite  = YamlConfiguration.loadConfiguration(main.getFile("kit"));

    	int nb = 0;
    	int elo = 0;
		while(nb != kite.getInt("kitnumber")) {
			nb++;
			if(kite.getBoolean("kit." + nb + ".act") == true && kite.getBoolean("kit." + nb + ".ranked") == true ) {
				elo = elo + getElo(player, "" + nb);
			}
		}
		elo = Math.round(elo / main.rankit.size());
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE stat SET elo="+ elo +" WHERE player_name = ? AND kit_id=0");
	        preparedStatement.setString(1, player);
	        preparedStatement.execute();
	        preparedStatement.close();

		} catch (SQLException e) {
			try {
		        preparedStatement = connection.prepareStatement("INSERT INTO stat (`player_name`, `kit_id`, `elo`) VALUES ('" + player +"', '0', ' "+ elo +"')");
		        preparedStatement.execute();
		        preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
        getelo.remove(player + "_0");
        getelo.put(player + "_0",  elo+ "");

    }
    
    public void Kitadd(String id, Boolean rank, String name) {
    
    	PreparedStatement pr;
    	String nom = null;
    	
    	if(rank) {
    		nom = "rn_" + id;
    	}else {
    		nom = "un_" + id;
    	}
		try {
			pr = connection.prepareStatement("ALTER TABLE stat ADD " + nom + " INT NOT NULL DEFAULT 0");
			pr.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(!rank) {
			try {
	
	        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kits (id,name) VALUES (?,?)");
	        preparedStatement.setInt(1, Integer.parseInt(id));
	        preparedStatement.setString(2, name);
	        preparedStatement.execute();
	        preparedStatement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
        

    	
    }
    
    public void actKit(Boolean rank, String id, Boolean act) {
    	
    	if(rank) {
			try {
				
		        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kits SET ranked=" + act + " WHERE id=" + Integer.parseInt(id));
		        preparedStatement.execute();
		        preparedStatement.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 	}
    	
    	else{
			try {
				
		        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kits SET activ=" + act + " WHERE id=" + Integer.parseInt(id));
		        preparedStatement.execute();
		        preparedStatement.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 	}
    	
    }
        
    public void Stat(String id, Player player) {

    	int in = 0;
    	int st = 0;

    	try {
			PreparedStatement prs = connection.prepareStatement("SELECT id FROM users WHERE uuid = ?");
			prs.setString(1, player.getUniqueId().toString());
			ResultSet rs = prs.executeQuery();
			prs.close();
	        while(rs.next()) {
	        	 in = rs.getInt("id");
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT un_"+ id +"  FROM stat WHERE id = ?");
			ps.setInt(1, in);
	        ResultSet rs = ps.executeQuery();
			ps.close();
	        while(rs.next()) {
	        	st = rs.getInt("un_" + id);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	st++;
		
    	try {
	        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stat SET un_" + id +"=" + st + " WHERE id=" + in);
	        preparedStatement.execute();
	        preparedStatement.close();
		} catch (SQLException e1) {
				e1.printStackTrace();
		}
    }


}
