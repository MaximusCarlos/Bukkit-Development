package me.maxipad.counter.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.maxipad.counter.KillCounter;

public class SideBoard implements Listener {

	private KillCounter plugin;

	public SideBoard(KillCounter pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		setupScoreboard(p);
	}

	@SuppressWarnings({ "deprecation" })
	public void setupScoreboard(Player p) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard onJoin = sm.getNewScoreboard();
		Objective o = onJoin.registerNewObjective("dash", "playerKillCount");

		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName("" + ChatColor.GRAY + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "<---" + ChatColor.AQUA
				+ ChatColor.BOLD + "Stats" + ChatColor.GRAY + ChatColor.BOLD + ChatColor.STRIKETHROUGH + "--->");

		Score spacer = null;

		Score nameTitle = null;
		Score name = null;

		// Score spacer2 = null;

		Score killTitle = null;
		Score kill = null;

		// Score spacer3 = null;

		Score deathTitle = null;
		Score death = null;

		// Score spacer4 = null;

		Score pointsTitle = null;
		Score points = null;

		String uuid = p.getUniqueId().toString();
		int kills = plugin.getConfig().getInt("Players." + uuid + ".Kills");
		int deaths = plugin.getConfig().getInt("Players." + uuid + ".Deaths");
		int Points = plugin.getConfig().getInt("Players." + uuid + ".Points");
		String KILLS = Integer.toString(kills);
		String DEATHS = Integer.toString(deaths);
		String POINTS = Double.toString(Points);
		try {

			spacer = o.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + ""));
			spacer.setScore(9);

			nameTitle = o.getScore(ChatColor.GOLD + "Name:");
			nameTitle.setScore(8);

			name = o.getScore(p.getName());
			name.setScore(7);

			killTitle = o.getScore(ChatColor.GOLD + "Kills:");
			killTitle.setScore(6);

			kill = o.getScore(KILLS);
			kill.setScore(5);

			deathTitle = o.getScore(ChatColor.GOLD + "Deaths:");
			deathTitle.setScore(4);

			death = o.getScore(DEATHS);
			death.setScore(3);

			pointsTitle = o.getScore(ChatColor.GOLD + "Points:");
			pointsTitle.setScore(2);
			
			points = o.getScore(POINTS);
			points.setScore(1);

			p.setScoreboard(onJoin);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
