package fr.jejeoh.practice;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.jejeoh.practice.Arene.AreneManager.Arene;
import fr.jejeoh.practice.Arene.AreneManager.Int;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Scoreboard extends BukkitRunnable {
	
	private Base main;
	
	public Scoreboard(Base main) {
		this.main = main; 
	}
	



	
	EntityPlayer msplayer = null;

	
	@Override
	public void run() {
		
		for(Player play : Bukkit.getOnlinePlayers()) {
			
			if(!(main.online.contains(play))) {
				
				final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
				final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
				final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");
				
				objetctive.setDisplayName("§f-- §6§lErizia§f --");
				objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);
	
				
				final Score vide3 = objetctive.getScore("  ");
				final Score grade = objetctive.getScore("§6| §3Grade : ");
				final Score vide2 = objetctive.getScore(" ");
				final Score joueur = objetctive.getScore("§6| §aConnecter : §f" + Bukkit.getOnlinePlayers().size());
				final Score enjeu = objetctive.getScore("§6| §cEn jeu : §f" + main.online.size());
				final Score vide = objetctive.getScore("");
				final Score pub = objetctive.getScore("§f§lerizia.fr");
		
				vide3.setScore(6);
				grade.setScore(5);
				vide2.setScore(4);
				joueur.setScore(3);
				enjeu.setScore(2);
				vide.setScore(1);
				pub.setScore(0);
				
				
				play.setScoreboard(scoreboard);

			}else {
				final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
				final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
				final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");

				
				objetctive.setDisplayName("§f-- §6§lErizia§r§f --");
				objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);
				
	
				
				final Score vide3 = objetctive.getScore("  ");
				final Score opposant = objetctive.getScore("§6| §aAdversaire : §f" + main.game.get(play).getName());
				final Score vide2 = objetctive.getScore(" ");
				msplayer = ((CraftPlayer)play).getHandle();
				final Score ping1 = objetctive.getScore("§6| §aVotre ping : §f" + msplayer.ping + "ms");
				msplayer = ((CraftPlayer)main.game.get(play)).getHandle();
				final Score ping2 = objetctive.getScore("§6| §cPing adverse : §f" + msplayer.ping + "ms");
				final Score vide = objetctive.getScore("");
				final Score pub = objetctive.getScore("§f§lerizia.fr");
		
				vide3.setScore(6);
				opposant.setScore(5);
				vide2.setScore(4);
				ping1.setScore(3);
				ping2.setScore(2);
				vide.setScore(1);
				pub.setScore(0);
				play.setScoreboard(scoreboard);
			}

		}
		
		for(Int it : main.timerstart) {
			if(it.getInt() == 0) return;
			it.moinInt();
			System.out.println("" + it.getInt());
			Player player = main.timerst.get(it);
			Player play = main.game.get(player);
			if(it.getInt() == 0) {
				Arene are = null;
				if(main.end.containsKey(player)) {
					are = main.end.get(player);
				}else if(main.end.containsKey(play)) {
					are = main.end.get(play);
				}
				
				play.teleport(are.getFirst());
				player.teleport(are.getSecond());
				
				player.setLevel(it.getInt());
				play.setLevel(it.getInt());
				
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				play.playSound(play.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

				
				main.att.remove(play);
				main.att.remove(player);
				
				main.timerstart.remove(it);
				
			}else {
				player.setLevel(it.getInt());
				play.setLevel(it.getInt());
				
				player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
				play.playSound(play.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}
		}
		

	}


}
