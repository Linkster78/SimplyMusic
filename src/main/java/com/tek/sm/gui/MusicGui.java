package com.tek.sm.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.InventoryUtils;
import com.tek.sm.util.ItemUtil;
import com.tek.sm.util.Reference;
import com.xxmicloxx.NoteBlockAPI.Song;

public class MusicGui{

	private Inventory inventory;
	private int page;
	private String filter;
	private Song song;
	
	public MusicGui(int page, Player player, String filter) {
		this.inventory = Bukkit.createInventory(null, 54, Reference.INVENTORY_TITLE + ChatColor.DARK_PURPLE + " Filter: <" + filter + "> " + ChatColor.GREEN + " Page " + page);
		this.page = page;
		this.song = SimplyMusic.inst().getSessionManager().getSession(player) == null ? null : SimplyMusic.inst().getSessionManager().getSession(player).getSongPlaying();
		this.filter = filter;
		
		init();
	}

	public void init() {
		InventoryUtils.fillHorizontal(inventory, Reference.SEPARATOR, 4);
		if(canGoBack()) inventory.setItem(InventoryUtils.slot(0, 4), Reference.BACK);
		if(canGoNext()) inventory.setItem(InventoryUtils.slot(8, 4), Reference.NEXT);
		inventory.setItem(InventoryUtils.slot(0, 5), Reference.STOP);
		inventory.setItem(InventoryUtils.slot(1, 5), Reference.SKIP);
		inventory.setItem(InventoryUtils.slot(3, 5), Reference.FILTER);
		inventory.setItem(InventoryUtils.slot(4, 5), Reference.PLAY);
		inventory.setItem(InventoryUtils.slot(5, 5), Reference.VOLUME);
		inventory.setItem(InventoryUtils.slot(6, 5), Reference.PLAYLIST);
		inventory.setItem(InventoryUtils.slot(7, 5), Reference.SHUFFLE);
		inventory.setItem(InventoryUtils.slot(8, 5), Reference.TUNE);
		
		int start = 36 * (page - 1);
		
		ArrayList<Song> songs = SimplyMusic.inst().getSongManager().getSongsWithFilter(filter);
		
		for(int i = start; i < Math.min(start + 36, songs.size()); i++) {
			Song song = songs.get(i);
			
			inventory.setItem(i - start, this.song != null && this.song == song ? ItemUtil.glow(Reference.randomDisk(song)) : Reference.randomDisk(song));
		}
	}
	
	public boolean canGoBack() {
		return this.page > 1;
	}
	
	public boolean canGoNext() {
		return this.page < pages();
	}
	
	public int pages() {
		return (int) Math.ceil((float)SimplyMusic.inst().getSongManager().getSongsWithFilter(filter).size() / 36);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static boolean isMusicGui(Inventory inventory) {
		if(inventory.getTitle() == null) return false;
		return inventory.getTitle().startsWith(Reference.INVENTORY_TITLE);
	}
	
	public static int getPage(Inventory inventory) {
		return Integer.parseInt(inventory.getTitle().split(" ")[inventory.getTitle().split(" ").length - 1]);
	}
	
	public static String getFilter(Inventory inventory) {
		return inventory.getTitle().split("<")[1].split(">")[0];
	}

}
