package com.tek.sm.util;

public enum CommandDescriptions {
	HELP("Displays the help menu"),
	RELOAD("Reloads the plugin"),
	LIST("Lists all available songs"),
	PLAY("Plays every song one after the other"),
	PLAYID("Plays the song with the specified id"),
	TUNE("Listens along with the specified player"),
	STOP("Stops song playback"),
	SHUFFLE("Plays random songs one after the other"),
	SKIP("Skips the current song"),
	GUI("For users who prefer a cool interface"),
	IMPORT("Imports a song from a direct link to a .nbs with the name specified"),
	VOLUME("Changes your volume from 0 to 100");
	
	private final String text;
	CommandDescriptions(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
