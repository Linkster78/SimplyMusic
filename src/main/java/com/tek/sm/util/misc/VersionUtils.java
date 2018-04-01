package com.tek.sm.util.misc;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VersionUtils {
	
	public static void playSuccess(Player p) {
		if(contains("BLOCK_NOTE_XYLOPHONE")) {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
		}else {
			try{
				p.playSound(p.getLocation(), Sound.valueOf("NOTE_BASS"), 1, 1);
			}catch(Exception e) { }
		}
	}
	
	public static void playError(Player p) {
		if(contains("BLOCK_ANVIL_PLACE")) {
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
		}else {
			try{
				p.playSound(p.getLocation(), Sound.valueOf("ANVIL_LAND"), 1, 1);
			}catch(Exception e) { }
		}
	}
	
	public static void playPop(Player p) {
		if(contains("ENTITY_CHICKEN_EGG")) {
			p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
		}else {
			try{
				p.playSound(p.getLocation(), Sound.valueOf("CHICKEN_EGG_POP"), 1, 1);
			}catch(Exception e) { }
		}
	}
	
	private static boolean contains(String name) {
		for(Sound s : Sound.values()) {
			if(s.name().equalsIgnoreCase(name)) return true;
		}
		
		return false;
	}
	
}
