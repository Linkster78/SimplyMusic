package com.tek.sm.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.tek.sm.util.Reference;
import com.tek.sm.util.lang.Lang;
import com.tek.sm.util.misc.InventoryUtils;
import com.tek.sm.util.misc.ItemUtil;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;

public class VolumeGui{

	private Inventory inventory;
	private Player player;
	
	public VolumeGui(Player player) {
		this.inventory = Bukkit.createInventory(null, 27, Lang.translate("title_volume"));
		this.player = player;
		
		init();
	}

	public void init() {
		inventory.setItem(InventoryUtils.slot(4, 2), Reference.BACKMAIN);
		
		int volume = NoteBlockPlayerMain.getPlayerVolume(player);
		
		for(int i = 0; i < 9; i++) {
			int itemVolume = (i + 2) * 10;
			short damage = (short) ((float)volume >= itemVolume ? 5 : 14);
			
			ChatColor color = ((float)volume >= itemVolume ? ChatColor.GREEN : ChatColor.RED);
			
			ItemStack item = ItemUtil.createItemStack(Material.STAINED_GLASS_PANE, 1, damage, color.toString() + itemVolume + "%");
			
			inventory.setItem(InventoryUtils.slot(i, 1), item);
		}
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static boolean isVolumeGui(Inventory inventory) {
		if(inventory.getTitle() == null) return false;
		return inventory.getTitle().startsWith(Lang.translate("title_volume"));
	}
	
}
