package com.tek.sm.management;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.tek.sm.SimplyMusic;
import com.tek.sm.playlists.Playlist;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class PlayerSession {
	
	public UUID uuid;
	public UUID targetUUID;
	public SongPlayer sp;
	public boolean shuffle;
	public boolean consec;
	public boolean loop;
	public boolean playliste;
	public int song, playlist;
	public Playlist[] playlists;
	
	public PlayerSession(UUID uuid) {
		this.uuid = uuid;
		this.targetUUID = this.uuid;
		this.shuffle = false;
		this.consec = false;
		this.loop = false;
		this.playliste = false;
		this.playlists = new Playlist[3];
		this.song = 0;
		this.playlist = -1;
	}
	
	public void close(boolean reset) {
		if(reset) {
			this.shuffle = false;
			this.consec = false;
			this.loop = false;
			this.playliste = false;
			this.song = 0;
			this.playlist = -1;
		}

		if(this.sp != null) {
			sp.destroy();
			this.sp = null;
		}else {
			if(this.targetUUID != this.uuid) {
				PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(this.targetUUID);
				
				if(session != null) {
					if(session.sp != null) {
						session.sp.removePlayer(player());
					}
				}
				
				this.targetUUID = this.uuid;
			}
		}
		
		for(Player player : SimplyMusic.inst().getSongManager().getPlayingPlayers(player())) {
			PlayerSession sess = SimplyMusic.inst().getSessionManager().getSession(player);
			
			if(sess != null && sess.sp != null && player() != null && sess.sp.getPlayerList().contains(player().getName())) sess.sp.removePlayer(player());
		}
	}
	
	public Playlist getPlaylist(int index) {
		return playlists[index];
	}
	
	public void playSong(Song song, boolean reset) {
		close(reset);
		
		this.targetUUID = uuid;
		
		this.sp = new RadioSongPlayer(song);
		this.sp.addPlayer(player());
		this.sp.setPlaying(true);
	}
	
	public void tune(Player player) {
		close(true);
		
		this.targetUUID = player.getUniqueId();
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(this.targetUUID);
		session.sp.addPlayer(player());
	}
	
	public void shuffle() {
		close(true);
		
		this.consec = false;
		this.shuffle = true;
		this.loop = false;
		this.playliste = false;
		
		next();
	}
	
	public void loop(Song song) {
		close(true);
		
		this.consec = false;
		this.shuffle = false;
		this.loop = true;
		this.playliste = false;
		this.song = SimplyMusic.inst().getSongManager().getIndex(song);
		this.playlist = -1;
		
		next();
	}
	
	public void consec() {
		close(true);
		
		this.shuffle = false;
		this.consec = true;
		this.loop = true;
		this.playliste = false;
		this.song = -1;
		this.playlist = -1;
		
		next();
	}
	
	public void next() {
		boolean playing = this.sp == null ? false : this.sp.isPlaying();
		int index = this.sp == null ? -1 : SimplyMusic.inst().getSongManager().getIndex(this.sp.getSong());
		
		close(false);
		
		if(!this.shuffle && !this.consec && !this.loop) {
			if(playing) {
				index++;
				if(index == SimplyMusic.inst().getSongManager().amount()) index = 0;
				this.playSong(SimplyMusic.inst().getSongManager().getSong(index), false);
			}
		}
		
		if(this.shuffle) {
			Song song = SimplyMusic.inst().getSongManager().randomSong();
			playSong(song, false);
		}
		
		if(this.consec) {
			song++;
			if(song == SimplyMusic.inst().getSongManager().amount()) song = 0;
			Song song = SimplyMusic.inst().getSongManager().getSong(this.song);
			playSong(song, false);
		}
		
		if(this.loop) {
			playSong(SimplyMusic.inst().getSongManager().getSong(song), false);
		}
		
		if(this.playliste) {
			song++;
			if(song == playlists[playlist].getSongs().size()) song = 0;
			Song song = playlists[playlist].atPos(this.song);
			playSong(song, false);
		}
	}
	
	public void playList(Playlist playlist) {
		close(true);
			
		this.consec = false;
		this.shuffle = false;
		this.loop = false;
		this.playliste = true;
		this.song = -1;
		this.playlist = playlistIndex(playlist);
			
		next();
	}
	
	public void createSettings() {
		File folder = new File("plugins/" + SimplyMusic.inst().getName());
		if(!folder.exists()) folder.mkdir();
		File userFolder = new File("plugins/" + SimplyMusic.inst().getName() + "/users");
		if(!userFolder.exists()) userFolder.mkdir();
		File file = new File("plugins/SimplyMusic/users/" + this.uuid.toString() + ".yml");
		
		boolean justCreated = false;
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				justCreated = true;
			} catch (IOException e) { }
		}
		
		YamlConfiguration settings = new YamlConfiguration();
		try {
			settings.load(file);
			
			if(settings.getConfigurationSection("playlists") == null) {
				for(int i = 0; i < 3; i++) { settings.set("playlists.pl_" + i, Playlist.createEmpty().encode()); }
			}
			
			settings.save(file);
		} catch (IOException | InvalidConfigurationException e) { }
		
		if(justCreated) {
			settings.set("volume", 100);
			for(int i = 0; i < 3; i++) { settings.set("playlists.pl_" + i, Playlist.createEmpty().encode() ); }
			
			try {
				settings.save(file);
			} catch (IOException e) { }
		}
	}
	
	public void saveSettings() {
		//IF FILE WAS DELETED, RECREATE
		createSettings();
		
		File file = new File("plugins/SimplyMusic/users/" + this.uuid.toString() + ".yml");
		YamlConfiguration settings = new YamlConfiguration();
		try {
			settings.load(file);
		} catch (IOException | InvalidConfigurationException e) { }
		
		settings.set("volume", NoteBlockPlayerMain.getPlayerVolume(player()));
		
		for(int i = 0; i < 3; i++) {
			settings.set("playlists.pl_" + i, playlists[i].encode());
		}
		
		try {
			settings.save(file);
		} catch (IOException e) { }
	}
	
	public void loadSettings() {
		File file = new File("plugins/SimplyMusic/users/" + this.uuid.toString() + ".yml");
		YamlConfiguration settings = new YamlConfiguration();
		try {
			settings.load(file);
		} catch (IOException | InvalidConfigurationException e) { }
		
		NoteBlockPlayerMain.setPlayerVolume(player(), (byte) settings.getInt("volume"));
		
		for(int i = 0; i < 3; i++) {
			this.playlists[i] = new Playlist(settings.getString("playlists.pl_" + i));
		}
	}
	
	public boolean isListening() {
		if(this.sp != null) {
			return this.sp.isPlaying();
		}else {
			if(this.targetUUID != this.uuid) {
				PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(this.targetUUID);
				
				if(session != null) {
					if(session.sp != null) {
						return session.sp.isPlaying();
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean isPlaying() {
		return this.sp == null ? false : this.sp.isPlaying();
	}
	
	public Song getSongPlaying() {
		return isPlaying() ? this.sp.getSong() : null;
	}
	
	public Song getSongListening() {
		return isListening() ? isPlaying() ? getSongPlaying() : SimplyMusic.inst().getSessionManager().getSession(this.targetUUID).getSongPlaying() : null;
	}
	
	public int playlistIndex(Playlist playlist) {
		for(int i = 0; i < 3; i++) {
			if(playlists[i].equals(playlist)) return i;
		}
		
		return -1;
	}
	
	public Player player() {
		return SimplyMusic.inst().getServer().getPlayer(uuid);
	}
	
}
