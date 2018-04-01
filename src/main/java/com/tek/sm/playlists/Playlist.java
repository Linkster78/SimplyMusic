package com.tek.sm.playlists;

import java.util.ArrayList;

import com.tek.sm.SimplyMusic;
import com.xxmicloxx.NoteBlockAPI.Song;

public class Playlist {
	
	private ArrayList<String> songs;
	
	public Playlist(String section) {
		this.songs = new ArrayList<String>();
		
		String[] songs = section.split("@");
		for(String song : songs) {
			if(song == null || !song.equals("")) this.songs.add(song);
		}
	}
	
	private Playlist() {
		this.songs = new ArrayList<String>();
	}
	
	public void addSong(Song song) {
		String id = SimplyMusic.inst().getSongManager().songid(song);
		if(!isFull()) songs.add(id);
	}
	
	public void removeSong(Song song) {
		String id = SimplyMusic.inst().getSongManager().songid(song);
		if(songs.contains(id)) songs.remove(id);
	}
	
	public ArrayList<Song> getSongs(){
		ArrayList<Song> songs = new ArrayList<Song>();
		
		for(String songid : new ArrayList<String>(this.songs)) {
			Song song = SimplyMusic.inst().getSongManager().songById(songid);
			
			if(song == null) {
				this.songs.remove(songid);
			}else {
				songs.add(song);
			}
		}
		
		return songs;
	}
	
	public boolean isFull() {
		return songs.size() >= 27;
	}
	
	public String encode() {
		if(songs.isEmpty()) return "@";
		
		String encoded = "";
		for(String song : songs) {
			encoded += song + "@";
		}
		
		return encoded.substring(0, encoded.length() - 1);
	}
	
	public Song atPos(int index) {
		String songId;
		return (songId = songs.get(index)) == null ? null : SimplyMusic.inst().getSongManager().songById(songId);
	}
	
	public static Playlist createEmpty() {
		return new Playlist();
	}
	
}
