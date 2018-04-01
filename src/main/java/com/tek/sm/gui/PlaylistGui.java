package com.tek.sm.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.tek.sm.SimplyMusic;
import com.tek.sm.playlists.Playlist;
import com.tek.sm.util.InventoryUtils;
import com.tek.sm.util.Reference;
import com.xxmicloxx.NoteBlockAPI.Song;

public class PlaylistGui{

	private Inventory inventory;
	private int page, playlist;
	private Player player;
	
	public PlaylistGui(int page, int playlist, Player player) {
		this.inventory = Bukkit.createInventory(null, 54, Reference.PLAYLIST_TITLE + ChatColor.BLUE + " Playlist " + (playlist + 1) + ChatColor.GREEN + " Page " + page);
		this.page = page;
		this.playlist = playlist;
		this.player = player;
		
		init();
	}

	public void init() {
		InventoryUtils.fillHorizontal(inventory, Reference.SEPARATOR, 3);
		if(canGoBack()) inventory.setItem(InventoryUtils.slot(0, 3), Reference.BACK);
		if(canGoNext()) inventory.setItem(InventoryUtils.slot(8, 3), Reference.NEXT);
		inventory.setItem(InventoryUtils.slot(4, 5), Reference.BACKMAIN);
		inventory.setItem(InventoryUtils.slot(0, 5), Reference.PLAY);
		inventory.setItem(InventoryUtils.slot(8, 5), Reference.STOP);
		
		int start = 9 * (page - 1);
		
		ArrayList<Song> songs = SimplyMusic.inst().getSongManager().getSongs();
		
		for(int i = start; i < Math.min(start + 9, songs.size()); i++) {
			Song song = songs.get(i);
			
			inventory.setItem(InventoryUtils.slot(0, 4) + i - start, Reference.addingDisk(song));
		}
		
		Playlist playlist = SimplyMusic.inst().getSessionManager().getSession(player).getPlaylist(this.playlist);
		ArrayList<Song> playlistSongs = playlist.getSongs();
		
		for(int i = 0; i < Math.min(27, playlistSongs.size()); i++) {
			Song song = playlistSongs.get(i);
			ItemStack item = Reference.playlistDisk(song);
			inventory.setItem(i, item);
		}
	}
	
	public boolean canGoBack() {
		return this.page > 1;
	}
	
	public boolean canGoNext() {
		return this.page < pages();
	}
	
	public int pages() {
		return (int) Math.ceil((float)SimplyMusic.inst().getSongManager().amount() / 9);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static boolean isPlaylistGui(Inventory inventory) {
		if(inventory.getTitle() == null) return false;
		return inventory.getTitle().startsWith(Reference.PLAYLIST_TITLE);
	}
	
	public static int getPage(Inventory inventory) {
		return Integer.parseInt(inventory.getTitle().split(" ")[inventory.getTitle().split(" ").length - 1]);
	}
	
	public static int getPlaylist(Inventory inventory) {
		return Integer.parseInt(ChatColor.stripColor(inventory.getTitle().split(" ")[inventory.getTitle().split(" ").length - 3])) - 1;
	}

}
