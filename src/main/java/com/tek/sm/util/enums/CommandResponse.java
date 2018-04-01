package com.tek.sm.util.enums;

public enum CommandResponse {
	NEPERMISSIONS("Not enough permissions"),
	NPLAYER("You must be a player"),
	UNKNOWN("Unknown subcommand"),
	IARGS("Invalid amount of arguments"),
	IINT("Invalid number"),
	ISONG("Unknown song"),
	IPLAYER("This player doesn't exist or isn't online"),
	NSONGS("There are no songs to play through"),
	YPLAYER("You can't listen along with yourself"),
	NLPLAYER("This player isn't playing music"),
	NPLAYING("You are not playing any music"),
	IVOLUME("The volume must be from 0 to 100!"),
	FPLAYLIST("This playlist is full"),
	IDOWNLOAD("There was an issue while downloading the music "),
	INTERRUPTED("Your songs were interrupted due to a music reload"),
	EPLAYLIST("This playlist is empty"),
	RELOADING("Reloading plugin..."),
	RELOADED("Reloaded plugin!"),
	NOWPLAYING("Now playing: "),
	NOWLOOPING("Now looping: "),
	STOPPED("Stopped song playback"),
	TUNED("You are now listening along with "),
	SHUFFLED("Now shuffling through the song list"),
	SKIPPED("Skipped the current song"),
	ADDED("Added the song to the playlist "),
	REMOVED("Removed the song from the playlist "),
	PLAYING("Now playing through the song list"),
	LISTENING("Now listening to "),
	OPENED("Opened the music gui"),
	DOWNLOADING("Downloading your song..."),
	DOWNLOADED("Downloaded the song "),
	CVOLUME("Changed your volume to "),
	WTF("Something very bad happened. Contact the developer right now.");
	
	private final String text;
	CommandResponse(final String text){
		this.text = text;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
