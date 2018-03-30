package com.tek.sm.util;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_PURPLE;

public enum MiscText {
	CLEARFILTER("Right click to clear filter"),
	TITLEINVENTORY(DARK_GRAY + "[" + DARK_PURPLE + "Simply" + BLUE + "Music" + DARK_GRAY + "]"),
	TITLEVOLUME(DARK_GRAY + "[" + DARK_PURPLE + "Volume" + BLUE + " Menu" + DARK_GRAY + "]"),
	TITLETUNE(DARK_GRAY + "[" + DARK_PURPLE + "Tune" + BLUE + " Menu" + DARK_GRAY + "]");
	
	private final String text;
	MiscText(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
