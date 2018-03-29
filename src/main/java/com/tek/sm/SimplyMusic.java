package com.tek.sm;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.tek.sm.commands.MusicCommand;
import com.tek.sm.commands.MusicTabCompleter;
import com.tek.sm.events.InventoryClick;
import com.tek.sm.events.PlayerJoin;
import com.tek.sm.events.PlayerQuit;
import com.tek.sm.loop.SongUpdater;
import com.tek.sm.management.PlayerSession;
import com.tek.sm.management.SessionManager;
import com.tek.sm.management.SongManager;
import com.tek.sm.util.Reference;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;

public class SimplyMusic extends JavaPlugin{
	
	public static Random random;
	private static SimplyMusic instance;
	private SongManager songManager;
	private SessionManager sessionManager;
	public NoteBlockPlayerMain nbpm;
	
	public void onEnable() {
		getConfig();
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		
		instance = this;
		
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		
		getCommand("music").setExecutor(new MusicCommand());
		getCommand("music").setTabCompleter(new MusicTabCompleter());
		
		nbpm = new NoteBlockPlayerMain();
		nbpm.enable();
		
		sessionManager = new SessionManager();
		
		songManager = new SongManager();
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SongUpdater(), 0, 5);
		
		random = new Random();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			SimplyMusic.inst().getSessionManager().createSession(player);
			PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
			
			session.createSettings();
			session.loadSettings();
		}
		
		Reference.loadLang(false);
		Reference.loadItems();
		
		songManager.reloadSongs();
	}
	
	public void onDisable() {
		nbpm.disable();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			SimplyMusic.inst().getSessionManager().createSession(player);
			PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
			
			session.saveSettings();
		}
	}
	
	public SongManager getSongManager() {
		return this.songManager;
	}
	
	public SessionManager getSessionManager() {
		return this.sessionManager;
	}
	
	public static void log(String message) {
		inst().getLogger().log(Level.INFO, message);
	}
	
	public static SimplyMusic inst() {
		return instance;
	}
	
}
