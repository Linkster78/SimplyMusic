package com.tek.sm.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tek.sm.SimplyMusic;
import com.tek.sm.management.PlayerSession;

public class PlayerQuit implements Listener{
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(e.getPlayer());
		if(session != null) session.close(true);
		session.saveSettings();
	}
	
}
