package com.tek.sm.util;

import static org.bukkit.ChatColor.*;

public enum CommandResponse {
	NEPERMISSIONS(RED + "Not enough permissions"),
	NPLAYER(RED + "You must be a player"),
	UNKNOWN(RED + "Unknown subcommand"),
	IARGS(RED + "Invalid amount of arguments"),
	IINT(RED + "Invalid number"),
	ISONG(RED + "Unknown song"),
	IPLAYER(RED + "This player doesn't exist or isn't online"),
	NSONGS(RED + "There are no songs to play through"),
	YPLAYER(RED + "You can't listen along with yourself"),
	NLPLAYER(RED + "This player isn't playing music"),
	NPLAYING(RED + "You are not playing any music"),
	IVOLUME(RED + "The volume must be from 0 to 100!"),
	IDOWNLOAD(RED + "There was an issue while downloading the music "),
	INTERRUPTED(RED + "Your songs were interrupted due to a music reload"),
	RELOADING(GREEN + "Reloading plugin..."),
	RELOADED(GREEN + "Reloaded plugin!"),
	NOWPLAYING(GREEN + "Now playing: "),
	STOPPED(GREEN + "Stopped song playback"),
	TUNED(GREEN + "You are now listening along with "),
	SHUFFLED(GREEN + "Now shuffling through the song list"),
	SKIPPED(GREEN + "Skipped the current song"),
	PLAYING(GREEN + "Now playing through the song list"),
	LISTENING(GREEN + "Now listening to "),
	OPENED(GREEN + "Opened the music gui"),
	DOWNLOADING(GREEN + "Downloading your song..."),
	DOWNLOADED(GREEN + "Downloaded the song "),
	CVOLUME(GREEN + "Changed your volume to "),
	WTF(RED + "Something very bad happened. Contact the developer right now.");
	
	private final String text;
	CommandResponse(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return Reference.PREFIX + text;
    }
}
