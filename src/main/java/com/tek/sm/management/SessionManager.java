package com.tek.sm.management;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class SessionManager {
	
	private ArrayList<PlayerSession> sessions;
	
	public SessionManager() {
		this.sessions = new ArrayList<PlayerSession>();
	}
	
	public void createSession(Player player) {
		if(getSession(player.getUniqueId()) == null) sessions.add(new PlayerSession(player.getUniqueId()));
	}
	
	public PlayerSession getSession(Player player) {
		for(PlayerSession session : sessions) {
			if(session.uuid.equals(player.getUniqueId())) return session;
		}
		
		return null;
	}
	
	public PlayerSession getSession(UUID uuid) {
		for(PlayerSession session : sessions) {
			if(session.uuid.equals(uuid)) return session;
		}
		
		return null;
	}
	
	public ArrayList<PlayerSession> getSessions(){
		return this.sessions;
	}
	
}
