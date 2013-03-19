package de.postcrafter.tutorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.postcrafter.tutorial.data.MySQL;

public class Ban implements CommandExecutor, Listener {

	private Tutorial plugin;
	
	private String reason = "Du wurdest gebannt!";
	
	public Ban(Tutorial plugin) {
		this.plugin = plugin;
		
		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("CREATE TABLE IF NOT EXISTS ban (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(16), reason VARCHAR(100))");
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("isbanned")) {
			if (args.length > 0) {
				String reason = this.getBanReason(args[0]);
				if (reason == null) {
					cs.sendMessage("§6" + args[0] + "§7 ist nicht gebannt.");
				} else {
					cs.sendMessage("§6" + args[0] + "§7 ist gebannt (§6" + reason + "§7)");
				}
			} else {
				cs.sendMessage("§c/isbanned [name]");
			}
			return true;
		} else if (label.equalsIgnoreCase("unban")) {
			if (args.length > 0) {
				if (this.getBanReason(args[0]) != null) {
					this.unban(args[0]);
					cs.sendMessage("§6" + args[0] + "§7 wurde entbannt.");
				} else {
					cs.sendMessage("§6" + args[0] + "§7 war nicht gebannt.");
				}
			} else {
				cs.sendMessage("§c/unban [name]");
			}
			return true;
		}
		if (args.length > 0) {
			String player = args[0];
			String reason = this.reason;
			if (args.length > 1) {
				reason = args[1];
				for (int i = 2; i < args.length; i++) {
					reason += " " + args[i];
				}
				reason = ChatColor.translateAlternateColorCodes('&', reason);
			}
			player = this.setBanned(player, reason);
			
			Player p = Bukkit.getPlayer(player);
			if (p != null) {
				p.kickPlayer(reason);
			}
			cs.sendMessage("§7Du hast §6" + player + "§7 gebannt (§6" + reason + "§7)");
		} else {
			cs.sendMessage("§c/ban [name] [reason]");
		}
		return true;
	}

	public String getBanReason(String player) {
		MySQL sql = this.plugin.getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String reason = null;
		try {
			st = conn.prepareStatement("SELECT * FROM ban WHERE name=?");
			st.setString(1, player);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				reason = rs.getString("reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return reason;
	}
	
	public String setBanned(String player, String reason) {
		MySQL sql = this.plugin.getMySQL();
		if (player.length() > 16) {
			player = player.substring(0, 16);
		}
		if (this.getBanReason(player) != null) {
			sql.queryUpdate("UPDATE ban SET reason='" + reason + "' WHERE name='" + player + "'");
		} else {
			sql.queryUpdate("INSERT INTO ban (name, reason) VALUES ('" + player + "', '" + reason + "')");
		}
		return player;
	}
	
	public void unban(String player) {
		MySQL sql = this.plugin.getMySQL();
		sql.queryUpdate("DELETE FROM ban WHERE name='" + player + "'");
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String reason = this.getBanReason(p.getName());
		if (reason != null) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage(reason);
		}
	}
}
