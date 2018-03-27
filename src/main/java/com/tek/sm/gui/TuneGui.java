package com.tek.sm.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.tek.sm.SimplyMusic;
import com.tek.sm.util.InventoryUtils;
import com.tek.sm.util.ItemUtil;
import com.tek.sm.util.Reference;

public class TuneGui{

	private Inventory inventory;
	private int page;
	private Player player;
	
	public TuneGui(int page, Player player) {
		this.inventory = Bukkit.createInventory(null, 36, Reference.TUNE_TITLE + ChatColor.GREEN + " Page " + page);
		this.page = page;
		this.player = player;
		
		init();
	}

	@SuppressWarnings("deprecation")
	public void init() {
		InventoryUtils.fillHorizontal(inventory, Reference.SEPARATOR, 3);
		inventory.setItem(InventoryUtils.slot(3, 3), Reference.BACKMAIN);
		inventory.setItem(InventoryUtils.slot(4, 3), Reference.STOP);
		inventory.setItem(InventoryUtils.slot(5, 3), Reference.REFRESH);
		if(canGoBack()) inventory.setItem(InventoryUtils.slot(0, 3), Reference.BACK);
		if(canGoNext()) inventory.setItem(InventoryUtils.slot(8, 3), Reference.NEXT);
		
		int start = 27 * (page - 1);
		
		ArrayList<Player> playing = SimplyMusic.inst().getSongManager().getPlayingPlayers(player);
		
		for(int i = start; i < Math.min(start + 27, playing.size()); i++) {
			SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);

			meta.setOwner(playing.get(i).getName());
			meta.setDisplayName(ChatColor.GREEN + playing.get(i).getName());
			
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "Listening to " + ChatColor.DARK_PURPLE + SimplyMusic.inst().getSongManager().songName(SimplyMusic.inst().getSessionManager().getSession(playing.get(i)).getSongPlaying()));
			lore.add(" ");
			lore.add(ChatColor.GOLD + "Listening along with");
			
			for(String user : SimplyMusic.inst().getSessionManager().getSession(playing.get(i)).sp.getPlayerList()) {
				if(!user.equals(playing.get(i).getName())) {
					lore.add(ChatColor.GREEN + "+ " + ChatColor.BLUE + user);
				}
			}
			
			meta.setLore(lore);

			ItemStack stack = new ItemStack(Material.SKULL_ITEM,1 , (byte)3);

			stack.setItemMeta(meta);
			
			inventory.setItem(i - start, SimplyMusic.inst().getSessionManager().getSession(player) != null && SimplyMusic.inst().getSessionManager().getSession(player).targetUUID == playing.get(i).getUniqueId() ? ItemUtil.glow(stack) : stack);
		}
	}
	
	public boolean canGoBack() {
		return this.page > 1;
	}
	
	public boolean canGoNext() {
		return this.page < pages();
	}
	
	public int pages() {
		return (int) Math.ceil((float)SimplyMusic.inst().getSongManager().getPlayingPlayers(player).size() / 27);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static boolean isTuneGui(Inventory inventory) {
		if(inventory.getTitle() == null) return false;
		return inventory.getTitle().startsWith(Reference.TUNE_TITLE);
	}
	
	public static int getPage(Inventory inventory) {
		return Integer.parseInt(inventory.getTitle().split(" ")[inventory.getTitle().split(" ").length - 1]);
	}

}
