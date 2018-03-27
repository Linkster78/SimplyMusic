package com.tek.sm.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.tek.sm.SimplyMusic;
import com.tek.sm.management.PlayerSession;

public class PlayerJoin implements Listener{
		
	@EventHandler
	public void join(PlayerJoinEvent e) {
		SimplyMusic.inst().getSessionManager().createSession(e.getPlayer());
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(e.getPlayer());
		
		session.createSettings();
		session.loadSettings();
	}
		
}
