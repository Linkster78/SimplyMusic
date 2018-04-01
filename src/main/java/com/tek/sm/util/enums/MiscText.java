package com.tek.sm.util.enums;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_PURPLE;

public enum MiscText {
	CLEARFILTER("Right click to clear filter"),
	LOOP("Right click to loop"),
	ADD("Left click to add to the playlist"),
	REMOVE("Left click to remove from the playlist"),
	TITLEINVENTORY(DARK_GRAY + "[" + DARK_PURPLE + "Simply" + BLUE + "Music" + DARK_GRAY + "]"),
	TITLEVOLUME(DARK_GRAY + "[" + DARK_PURPLE + "Volume" + BLUE + " Menu" + DARK_GRAY + "]"),
	TITLETUNE(DARK_GRAY + "[" + DARK_PURPLE + "Tune" + BLUE + " Menu" + DARK_GRAY + "]"),
	TITLEPLAYLIST(DARK_GRAY + "[" + DARK_PURPLE + "Playlist" + BLUE + " Menu" + DARK_GRAY + "]"),
	TITLECHOOSEPLAYLIST(BLUE + "Choose a playlist"),
	PREFIX(TITLEINVENTORY.toString() + ": ");
	
	private final String text;
	MiscText(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
