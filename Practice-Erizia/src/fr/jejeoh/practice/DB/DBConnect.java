package fr.jejeoh.practice.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 
	 int elo = 700;

        if (!hasAccont(p)) {
    		main.getInstance().title.sendTitle(p, 10, 60, 10, "§6Bienvenue");
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (uuid, player_name, elo) VALUES (?,?,?)");
                preparedStatement.setString(1, p.getUniqueId().toString());
                preparedStatement.setString(2, p.getName().toString());
                preparedStatement.setInt(3, elo);
                preparedStatement.execute();
                preparedStatement.close();
                
                
                PreparedStatement ps = connection.prepareStatement("SELECT id FROM users WHERE uuid=?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();

                int id = 0;
                
                while (rs.next()) {
                    id = rs.getInt("id");
                }
                
                PreparedStatement addacount = connection.prepareStatement("INSERT INTO stat (id) VALUES (?)");
                addacount.setInt(1, id);
                addacount.execute();
                addacount.close();


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
		int in = 0;
		
		if(kit.equalsIgnoreCase("0")) {
			try {
				preparedStatement = connection.prepareStatement("SELECT elo FROM users WHERE player_name = ?");
		        preparedStatement.setString(1, player);
		        rs = preparedStatement.executeQuery();
		        while(rs.next()) {
		        	in = rs.getInt("elo");
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	return in;

		}
		try {
			preparedStatement = connection.prepareStatement("SELECT elo_" + kit + " FROM users WHERE player_name = ?");
	        preparedStatement.setString(1, player);
	        rs = preparedStatement.executeQuery();
	        while(rs.next()) {
	        	in = rs.getInt("elo_" + kit);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return in;
    }
    public void addElo(String player, String kit, int elo) {
    	int nb = getElo(player, kit)+elo;
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE users SET elo_" + kit + "=" + nb +" WHERE player_name = ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    public void removeElo(String player, String kit, int elo) {
    	int nb = getElo(player, kit)-elo;
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE users SET elo_" + kit + "=" + nb +" WHERE player_name = ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    public void setElo(String player, String kit, int elo) {
        PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("UPDATE users SET elo_" + kit + "=" + elo +" WHERE player_name = ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.execute();

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
			preparedStatement = connection.prepareStatement("UPDATE users SET elo="+ elo +" WHERE player_name = ?");
	        preparedStatement.setString(1, player);
	        preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
