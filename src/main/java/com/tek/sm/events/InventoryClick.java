package com.tek.sm.events;

import static com.tek.sm.util.Reference.CVOLUME;
import static com.tek.sm.util.Reference.NEPERMISSIONS;
import static com.tek.sm.util.Reference.NPLAYING;
import static com.tek.sm.util.Reference.NSONGS;
import static com.tek.sm.util.Reference.PLAYING;
import static com.tek.sm.util.Reference.SHUFFLED;
import static com.tek.sm.util.Reference.SKIPPED;
import static com.tek.sm.util.Reference.TUNED;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.tek.sm.SimplyMusic;
import com.tek.sm.gui.MusicGui;
import com.tek.sm.gui.TuneGui;
import com.tek.sm.gui.VolumeGui;
import com.tek.sm.management.PlayerSession;
import com.tek.sm.util.CommandPermissions;
import com.tek.sm.util.InventoryUtils;
import com.tek.sm.util.Reference;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import com.xxmicloxx.NoteBlockAPI.Song;

import net.wesjd.anvilgui.AnvilGUI;

public class InventoryClick implements Listener{
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) return;
		
		Player p = (Player)e.getWhoClicked();
		
		if(MusicGui.isMusicGui(e.getInventory())) {
			e.setCancelled(true);
			
			boolean shouldRefresh = false;
			
			if(e.getCurrentItem() != null) {
				if(InventoryUtils.y(e.getRawSlot()) < 4) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						Song song = SimplyMusic.inst().getSongManager().getSongByItem(e.getCurrentItem());
						SimplyMusic.inst().getSongManager().playSong(p, song);
						
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
						p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(song));
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Reference.NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 4) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new MusicGui(MusicGui.getPage(e.getInventory()) - 1, p, MusicGui.getFilter(e.getInventory())).getInventory());
						
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 4) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new MusicGui(MusicGui.getPage(e.getInventory()) + 1, p, MusicGui.getFilter(e.getInventory())).getInventory());
						
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
						p.sendMessage(Reference.STOPPED.toString());
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Reference.NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 1 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.SKIP.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							if(SimplyMusic.inst().getSessionManager().getSession(p) != null && SimplyMusic.inst().getSessionManager().getSession(p).isPlaying()) {
								SimplyMusic.inst().getSongManager().next(p);
								
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
								p.sendMessage(SKIPPED.toString());
								PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(p);
								if(!session.shuffle && !session.consec) {
									p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(session.sp.getSong()));
								}
								
								shouldRefresh = true;
							}else {
								p.sendMessage(NPLAYING.toString());
								p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
							}
						}else {
							p.sendMessage(NSONGS.toString());
							p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 3 && InventoryUtils.y(e.getRawSlot()) == 5) {
					p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					
					if(e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.SHIFT_LEFT)) {
						new AnvilGUI(SimplyMusic.inst(), p, "Filter Here", (player, reply) -> {
							if(reply.contains("[") || reply.contains("]")) {
								p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
								p.openInventory(new MusicGui(1, p, "").getInventory());
								return "Filtered";
							}
						
							p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
							p.openInventory(new MusicGui(1, p, reply).getInventory());
						
							return "Filtered";
						});
					}else if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.SHIFT_RIGHT)){
						p.openInventory(new MusicGui(1, p, "").getInventory());
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.PLAY.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().playConsec(p);
							
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
							p.sendMessage(PLAYING.toString());
							
							shouldRefresh = true;
						}else {
							p.sendMessage(NSONGS.toString());
							p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 5 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						p.openInventory(new VolumeGui(p).getInventory());
						
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 7 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.SHUFFLE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().shuffle(p);
							
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
							p.sendMessage(SHUFFLED.toString());
							
							shouldRefresh = true;
						}else {
							p.sendMessage(NSONGS.toString());
							p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.TUNE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							p.openInventory(new TuneGui(1, p).getInventory());
							
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
						}else {
							p.sendMessage(NSONGS.toString());
							p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
						}
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
			}
			
			if(shouldRefresh) p.openInventory(new MusicGui(MusicGui.getPage(p.getOpenInventory().getTopInventory()), p, MusicGui.getFilter(e.getInventory())).getInventory());
		}
		
		if(TuneGui.isTuneGui(e.getInventory())) {
			e.setCancelled(true);
			
			boolean shouldRefresh = false;
			
			if(e.getCurrentItem() != null) {
				if(InventoryUtils.y(e.getRawSlot()) < 3) {
					if(p.hasPermission(CommandPermissions.TUNE.toString())) {
						if(e.getCurrentItem().getItemMeta() != null) {
							if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
								Player player = SimplyMusic.inst().getServer().getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
								
								SimplyMusic.inst().getSongManager().tune(p, player);
								
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
								p.sendMessage(TUNED.toString() + ChatColor.GOLD + player.getName());
						
								shouldRefresh = true;
							}
						}
					}else {
						p.sendMessage(Reference.NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new TuneGui(TuneGui.getPage(e.getInventory()) - 1, p).getInventory());
					
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					}
				}
			
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new TuneGui(TuneGui.getPage(e.getInventory()) + 1, p).getInventory());
					
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 3 && InventoryUtils.y(e.getRawSlot()) == 3) {
					p.openInventory(new MusicGui(1, p, "").getInventory());
						
					p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
						p.sendMessage(Reference.STOPPED.toString());
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Reference.NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 5 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
						
						shouldRefresh = true;
					}
				}
			}
			
			if(shouldRefresh) p.openInventory(new TuneGui(TuneGui.getPage(p.getOpenInventory().getTopInventory()), p).getInventory());
		}
		
		if(VolumeGui.isVolumeGui(e.getInventory())) {
			e.setCancelled(true);
			
			boolean shouldRefresh = false;
			
			if(e.getCurrentItem() != null) {
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 2) {
					p.openInventory(new MusicGui(1, p, "").getInventory());
						
					p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
				}
				
				if(InventoryUtils.y(e.getRawSlot()) == 1) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						int volume = (InventoryUtils.x(e.getRawSlot()) + 2) * 10;
						
						NoteBlockPlayerMain.setPlayerVolume(p, (byte)volume);
						
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
						p.sendMessage(CVOLUME.toString() + volume);
						
						shouldRefresh = true;
					}else {
						p.sendMessage(NEPERMISSIONS.toString());
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
					}
				}
			}
			
			if(shouldRefresh) p.openInventory(new VolumeGui(p).getInventory());
		}
	}
	
}
