package fr.jejeoh.practice;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Scoreboard extends BukkitRunnable {
	
	private Base main;
	
	public Scoreboard(Base main) {
		this.main = main; 
	}
	
	int sec = 0;


	
	EntityPlayer msplayer = null;

	
	@Override
	public void run() {
		
		sec++;
		
		for(Player play : Bukkit.getOnlinePlayers()) {
			
			
			if((main.online.contains(play)) && (!(main.groupe.ffa.containsKey(play)))) {
				final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
				final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
				final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");

				
				
				objetctive.setDisplayName("§f-- §6§lErizia§r§f --");
				objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);
				
	
				
				final Score vide3 = objetctive.getScore("  ");
				final Score opposant = objetctive.getScore("§6| §aAdversaire : §f" + main.game.get(play).getName());
				final Score vide2 = objetctive.getScore(" ");
				msplayer = ((CraftPlayer)play).getHandle();
				final Score ping1 = objetctive.getScore("§6| §aVotre ping : §f" + msplayer.ping + " §ams");
				msplayer = ((CraftPlayer)main.game.get(play)).getHandle();
				final Score ping2 = objetctive.getScore("§6| §cPing adverse : §f" + msplayer.ping + " §cms");
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

			}else if(main.ranonline.contains(play)) {
				final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
				final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
				final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");

				
				
				objetctive.setDisplayName("§f-- §6§lErizia§r§f --");
				objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);
				
	
				
				final Score vide3 = objetctive.getScore("  ");
				final Score opposant = objetctive.getScore("§6| §aAdversaire : §f" + main.game.get(play).getName());
				final Score vide2 = objetctive.getScore(" ");
				msplayer = ((CraftPlayer)play).getHandle();
				final Score ping1 = objetctive.getScore("§6| §aVotre ping : §f" + msplayer.ping + " §ams");
				msplayer = ((CraftPlayer)main.game.get(play)).getHandle();
				final Score ping2 = objetctive.getScore("§6| §cPing adverse : §f" + msplayer.ping + " §cms");
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

			}else {
			
				
				if(sec == 5) {
					
					int nb = main.online.size() + main.ranonline.size();
						
					
					final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
					final org.bukkit.scoreboard.Scoreboard scoreboard =  scoreboardManager.getNewScoreboard();
					final Objective objetctive = scoreboard.registerNewObjective("general", "dummy");
					
					objetctive.setDisplayName("§f-- §6§lErizia§f --");
					objetctive.setDisplaySlot(DisplaySlot.SIDEBAR);
		
					
					final Score bar2 = objetctive.getScore("§6§l―――――――――――――――――― ");
					final Score vide3 = objetctive.getScore("  ");
					final Score grade = objetctive.getScore("§6| Grade : ");
					final Score elo = objetctive.getScore("§6| Elo : §f" + main.getConnect().getElo(play.getName(), "0"));
					final Score vide2 = objetctive.getScore(" ");
					final Score vide4 = objetctive.getScore("     ");
					final Score joueur = objetctive.getScore("§6| §aConnectés : §f" + Bukkit.getOnlinePlayers().size());
					final Score enjeu = objetctive.getScore("§6| §cEn jeu : §f" + nb);
					final Score vide = objetctive.getScore("");
					final Score bar = objetctive.getScore("§6§l――――――――――――――――――");
					final Score pub = objetctive.getScore("§f§lerizia.fr");
			
					bar2.setScore(11);
					vide3.setScore(10);
					grade.setScore(9);
					vide4.setScore(8);
					elo.setScore(7);
					vide2.setScore(6);
					joueur.setScore(5);
					enjeu.setScore(3);
					vide.setScore(2);
					bar.setScore(0);
					pub.setScore(1);
					
					
					play.setScoreboard(scoreboard);
				}

			}

		}
		
		if(sec == 5) {
			sec = 0;
		}
		
		for(NumberTimer nt : main.timerstart) {
			nt.onDis();
			Player player = main.timerst.get(nt);
			Player play = main.game.get(player);
			if(!main.att.contains(player)) {
				main.timerstart.remove(nt);
				main.timerst.remove(nt);
			}
			if(nt.getTime() == 0) {
								
				player.setLevel(nt.getTime());
				play.setLevel(nt.getTime());
				
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				play.playSound(play.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

				
				main.att.remove(play);
				main.att.remove(player);
				
				main.timerstart.remove(nt);
				main.timerst.remove(nt);
				
				main.getInstance().title.sendTitle(player, 10, 20, 10, "§6Go");
				main.getInstance().title.sendTitle(play, 10, 20, 10, "§6Go");

				
			}else {
				if(nt.getTime() == 3) {
					main.getInstance().title.sendTitle(player, 10, 20, 10, "§c3");
					main.getInstance().title.sendTitle(play, 10, 20, 10, "§c3");
				}
				if(nt.getTime() == 2) {
					main.getInstance().title.sendTitle(player, 10, 20, 10, "§62");
					main.getInstance().title.sendTitle(play, 10, 20, 10, "§62");
				}
				if(nt.getTime() == 1) {
					main.getInstance().title.sendTitle(player, 10, 20, 10, "§e1");
					main.getInstance().title.sendTitle(play, 10, 20, 10, "§e1");
				}
				
				player.setLevel(nt.getTime());
				play.setLevel(nt.getTime());
				
				player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
				play.playSound(play.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}
		}
		for(NumberTimer nt : main.timeren) {
			nt.onDis();
			if(nt.getTime()== 0) {
				if(main.game.containsKey(main.timerend.get(nt))) {
					
				
					
					Player player = main.timerend.get(nt);
					Player play = main.game.get(player);
					if(!main.att.contains(player)) {
						main.timerstart.remove(nt);
						main.timerst.remove(nt);
					}
					main.att.remove(player);
					main.att.remove(play);

					main.game.remove(play);
					main.game.remove(player);

					if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					if(play.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
						play.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					
					Location loc = new Location(Bukkit.getWorld(main.getConfig().getString("spawn.world")), main.getConfig().getInt("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getInt("spawn.z"));
					player.teleport(loc);
					play.teleport(loc);
					
					main.onGive(player);
					main.onGive(play);

					main.timeren.remove(nt);
					main.timerend.remove(nt);
					
				}else {
					
				}
			}
		}


		
		
	}


}
