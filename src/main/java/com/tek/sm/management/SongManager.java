package com.tek.sm.management;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.Reference;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.Song;

public class SongManager {
	
	private ArrayList<Song> songs = new ArrayList<Song>();
	
	public void reloadSongs() {
		for(PlayerSession session : SimplyMusic.inst().getSessionManager().getSessions()) {
			if(session.isListening()) session.player().sendMessage(Reference.INTERRUPTED.toString());
			session.close(true);
		}
		
		songs.clear();
		
		File folder = new File("plugins/" + SimplyMusic.inst().getName());
		if(!folder.exists()) folder.mkdir();
		File songFolder = new File("plugins/" + SimplyMusic.inst().getName() + "/songs");
		if(!songFolder.exists()) songFolder.mkdir();
		File songDownloadFolder = new File("plugins/" + SimplyMusic.inst().getName() + "/songs/downloaded");
		if(!songDownloadFolder.exists()) songDownloadFolder.mkdir();
		
		System.gc();
		
		if(songFolder.listFiles() != null) {
			for(File file : songFolder.listFiles()) {
				if(file.isDirectory()) continue;
				
				Song s = loadSong(file);
				
				if(s == null) {
					System.gc();
					file.delete();
					SimplyMusic.log("Cleaned up song " + file.getName() + ", unused or corrupted");
				}
			}
		}
		
		if(songDownloadFolder.listFiles() != null) {
			for(File file : songDownloadFolder.listFiles()) {
				if(file.isDirectory()) continue;
				
				Song s = loadSong(file);
				
				if(s == null) {
					System.gc();
					file.delete();
					SimplyMusic.log("Cleaned up song " + file.getName() + ", unused or corrupted");
				}
			}
		}
		
		System.gc();
	}
	
	public Song loadSong(File file) {
		if(file.getName().endsWith(".nbs")) {
			Song song = NBSDecoder.parse(file);
			if(song != null) {
				for(Song isong : songs) {
					if(song.getTitle().equals(isong.getTitle()) && song.getSongHeight() == isong.getSongHeight() && song.getSpeed() == isong.getSpeed() && song.getDelay() == isong.getDelay()) {
						song = null;
						return null;
					}
				}
				
				songs.add(song);
				SimplyMusic.log("Loaded song " + songName(song));
				
				return song;
			}
		}
		
		return null;
	}
	
	final String ava = "abcdefghijklmnopqrstuvwxyz";
	
	public void downloadSong(final String url, final String name, final Player p) {
		try {
			int size = getSize(url);
			
			if(size > 1048576) {
				downloadResponse(p, null, "Too Large");
				return;
			}
			
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("plugins/" + SimplyMusic.inst().getName() + "/songs/downloaded/" + name + ".nbs");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			
			rbc = null; fos = null; System.gc();
			
			File file = new File("plugins/" + SimplyMusic.inst().getName() + "/songs/downloaded/" + name + ".nbs");
			Song s = loadSong(file);
			
			if(s == null) {
				System.gc();
				file.delete();
				SimplyMusic.log("Cleaned up song " + file.getName() + ", unused or corrupted");
				downloadResponse(p, s, "Corrupted");
				return;
			}
			
			downloadResponse(p, s, null);
		}catch(Exception e) { downloadResponse(p, null, e.getClass().getSimpleName()); }
	}
	
	public void downloadResponse(Player p, Song song, String error) {
		Player player = SimplyMusic.inst().getServer().getPlayer(p.getUniqueId());
		
		if(player == null) return;
		
		if(song != null) {
			player.sendMessage(Reference.DOWNLOADED.toString() + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songName(song));
		}else {
			player.sendMessage(Reference.IDOWNLOAD.toString() + ChatColor.GOLD + error);
		}
	}
	
	public int getSize(String urll) {
		int size = 0;
		
		try{
	    	URL url = new URL(urll);
	    	URLConnection conn = url.openConnection();
	    	size = conn.getContentLength();
	    	conn.getInputStream().close();
		}catch(Exception e) { }
	    
	    return size;
	}
	
	public void playSong(Player player, Song song) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.playSong(song, true);
	}
	
	public void tune(Player player, Player toTune) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.tune(toTune);
	}
	
	public void stop(Player player) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.close(true);
	}
	
	public void shuffle(Player player) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.shuffle();
	}
	
	public void playConsec(Player player) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.consec();
	}
	
	public void next(Player player) {
		SimplyMusic.inst().getSessionManager().createSession(player);
		PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(player);
		session.next();
	}
	
	public boolean isPlaying(Player player) {
		return SimplyMusic.inst().getSessionManager().getSession(player) == null ? false : SimplyMusic.inst().getSessionManager().getSession(player).isPlaying();
	}
	
	public Song getSong(int id) {
		if(id < songs.size()) {
			return songs.get(id);
		}
		
		return null;
	}
	
	public Song getSongByItem(ItemStack item) {
		for(Song song : songs) {
			if(item.getItemMeta() != null) {
				if(item.getItemMeta().getDisplayName() != null) {
					if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals(songName(song))) return song;
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Song> getSongsWithFilter(String filter){
		ArrayList<Song> songs = new ArrayList<Song>();
		
		for(Song song : this.songs) {
			if(containsIgnoreCase(songName(song), filter)) songs.add(song);
		}
		
		return songs;
	}
	
	private static boolean containsIgnoreCase(String str, String searchStr)     {
	    if(str == null || searchStr == null) return false;

	    final int length = searchStr.length();
	    if (length == 0)
	        return true;

	    for (int i = str.length() - length; i >= 0; i--) {
	        if (str.regionMatches(true, i, searchStr, 0, length))
	            return true;
	    }
	    return false;
	}
	
	public int getIndex(Song song) {
		int i = 0;
		
		for(Song isong : songs) {
			if(isong.equals(song)) return i;
			i++;
		}
		
		return 0;
	}
	
	public ArrayList<Player> getPlayingPlayers(Player himself){
		ArrayList<Player> playing = new ArrayList<Player>();
		
		for(Player player : SimplyMusic.inst().getServer().getOnlinePlayers()) {
			if(isPlaying(player) && player != himself) playing.add(player);
		}
		
		return playing;
	}
	
	public Song randomSong() {
		return getSong(SimplyMusic.random.nextInt(songs.size()));
	}
	
	public String songs() {
		StringBuilder songsBuilder = new StringBuilder();
		
		int i = 1;
		for(Song song : this.songs) {
			String str = DARK_GRAY + "[" + GOLD + i + DARK_GRAY + "]: " + GREEN + songName(song);
			
			songsBuilder.append(i == this.songs.size() ? str : str + "\n");
			
		    i++;
		}
		
		return songsBuilder.toString();
	}
	
	public Song songById(String songid) {
		for(Song song : songs) {
			if(songid(song).equals(songid)) return song;
		}
		
		return null;
	}
	
	public String nowPlaying(Song song) {
		return Reference.NOWPLAYING.toString() + ChatColor.GOLD + songName(song);
	}
	
	public int amount() {
		return songs.size();
	}
	
	public String songName(Song song) {
		return song.getTitle().isEmpty() ? song.getPath().getName().substring(0, song.getPath().getName().length() - 4) : song.getTitle();
	}
	
	public String songid(Song song) {
		return songName(song).toLowerCase().replaceAll(" ", "_");
	}
	
	public String songAuthor(Song song) {
		return song.getAuthor().isEmpty() ? "UNKNOWN" : song.getAuthor();
	}
	
}
