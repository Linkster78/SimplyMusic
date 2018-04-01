package com.tek.sm.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.tek.sm.SimplyMusic;
import com.tek.sm.gui.MusicGui;
import com.tek.sm.gui.PlaylistGui;
import com.tek.sm.gui.PlaylistSelectorGui;
import com.tek.sm.gui.TuneGui;
import com.tek.sm.gui.VolumeGui;
import com.tek.sm.management.PlayerSession;
import com.tek.sm.playlists.Playlist;
import com.tek.sm.util.enums.CommandPermissions;
import com.tek.sm.util.lang.Lang;
import com.tek.sm.util.misc.InventoryUtils;
import com.tek.sm.util.misc.VersionUtils;
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
					if(e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.SHIFT_LEFT)) {
						if(p.hasPermission(CommandPermissions.PLAY.toString())) {
							Song song = SimplyMusic.inst().getSongManager().getSongByItem(e.getCurrentItem());
							if(song == null) return;
							SimplyMusic.inst().getSongManager().playSong(p, song);
						
							VersionUtils.playSuccess(p);
							p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(song));
						
							shouldRefresh = true;
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
							VersionUtils.playError(p);
						}
					}else if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
						if(p.hasPermission(CommandPermissions.LOOP.toString())) {
							Song song = SimplyMusic.inst().getSongManager().getSongByItem(e.getCurrentItem());
							if(song == null) return;
							SimplyMusic.inst().getSongManager().loop(p, song);
						
							VersionUtils.playSuccess(p);
							p.sendMessage(SimplyMusic.inst().getSongManager().nowLooping(song));
						
							shouldRefresh = true;
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
							VersionUtils.playError(p);
						}
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 4) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new MusicGui(MusicGui.getPage(e.getInventory()) - 1, p, MusicGui.getFilter(e.getInventory())).getInventory());
						
						VersionUtils.playPop(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 4) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new MusicGui(MusicGui.getPage(e.getInventory()) + 1, p, MusicGui.getFilter(e.getInventory())).getInventory());
						
						VersionUtils.playPop(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						
						VersionUtils.playSuccess(p);
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("stopped"));
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 1 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.SKIP.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							if(SimplyMusic.inst().getSessionManager().getSession(p) != null && SimplyMusic.inst().getSessionManager().getSession(p).isPlaying()) {
								SimplyMusic.inst().getSongManager().next(p);
								
								VersionUtils.playSuccess(p);
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("skipped"));
								PlayerSession session = SimplyMusic.inst().getSessionManager().getSession(p);
								if(!session.shuffle && !session.consec) {
									p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(session.sp.getSong()));
								}
								
								shouldRefresh = true;
							}else {
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nplaying"));
								VersionUtils.playError(p);
							}
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
							VersionUtils.playError(p);
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 3 && InventoryUtils.y(e.getRawSlot()) == 5) {
					VersionUtils.playPop(p);
					
					if(e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.SHIFT_LEFT)) {
						new AnvilGUI(SimplyMusic.inst(), p, "Filter Here", (player, reply) -> {
							if(reply.contains("[") || reply.contains("]")) {
								VersionUtils.playPop(p);
								p.openInventory(new MusicGui(1, p, "").getInventory());
								return "Filtered";
							}
						
							VersionUtils.playPop(p);
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
							
							VersionUtils.playSuccess(p);
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("playing"));
							
							shouldRefresh = true;
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
							VersionUtils.playError(p);
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 5 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						p.openInventory(new VolumeGui(p).getInventory());
						
						VersionUtils.playPop(p);
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 6 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.PLAYLIST.toString())) {
						p.openInventory(new PlaylistSelectorGui().getInventory());
						
						VersionUtils.playPop(p);
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 7 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.SHUFFLE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							SimplyMusic.inst().getSongManager().shuffle(p);
							
							VersionUtils.playSuccess(p);
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("shuffled"));
							
							shouldRefresh = true;
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
							VersionUtils.playError(p);
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.TUNE.toString())) {
						if(SimplyMusic.inst().getSongManager().amount() != 0) {
							p.openInventory(new TuneGui(1, p).getInventory());
							
							VersionUtils.playSuccess(p);
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nsongs"));
							VersionUtils.playError(p);
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
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
								
								VersionUtils.playSuccess(p);
								p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("tuned") + ChatColor.GOLD + player.getName());
						
								shouldRefresh = true;
							}
						}
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new TuneGui(TuneGui.getPage(e.getInventory()) - 1, p).getInventory());
					
						VersionUtils.playPop(p);
					}
				}
			
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new TuneGui(TuneGui.getPage(e.getInventory()) + 1, p).getInventory());
					
						VersionUtils.playPop(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 3 && InventoryUtils.y(e.getRawSlot()) == 3) {
					p.openInventory(new MusicGui(1, p, "").getInventory());
						
					VersionUtils.playPop(p);
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						
						VersionUtils.playSuccess(p);
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("stopped"));
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 5 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						VersionUtils.playPop(p);
						
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
						
					VersionUtils.playPop(p);
				}
				
				if(InventoryUtils.y(e.getRawSlot()) == 1) {
					if(p.hasPermission(CommandPermissions.VOLUME.toString())) {
						int volume = (InventoryUtils.x(e.getRawSlot()) + 2) * 10;
						
						NoteBlockPlayerMain.setPlayerVolume(p, (byte)volume);
						
						VersionUtils.playSuccess(p);
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("cvolume") + volume);
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
			}
			
			if(shouldRefresh) p.openInventory(new VolumeGui(p).getInventory());
		}
		
		if(PlaylistGui.isPlaylistGui(e.getInventory())) {
			e.setCancelled(true);
			
			boolean shouldRefresh = false;
			
			if(e.getCurrentItem() != null) {
				if(InventoryUtils.y(e.getRawSlot()) < 3) {
					Song song = SimplyMusic.inst().getSongManager().getSongByItem(e.getCurrentItem());
					
					if(song != null) {
						Playlist playlist = SimplyMusic.inst().getSessionManager().getSession(p).getPlaylist(PlaylistGui.getPlaylist(e.getInventory()));
						
						playlist.removeSong(song);
						
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("removed") + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songName(song));
						VersionUtils.playSuccess(p);
						shouldRefresh = true;
					}
				}
				
				if(InventoryUtils.y(e.getRawSlot()) == 4) {
					Song song = SimplyMusic.inst().getSongManager().getSongByItem(e.getCurrentItem());
					
					if(song != null) {
						Playlist playlist = SimplyMusic.inst().getSessionManager().getSession(p).getPlaylist(PlaylistGui.getPlaylist(e.getInventory()));
						
						if(!playlist.isFull()) {
							playlist.addSong(song);
							
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("added") + ChatColor.GOLD + SimplyMusic.inst().getSongManager().songName(song));
							VersionUtils.playSuccess(p);
							shouldRefresh = true;
						}else {
							p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("fplaylist"));
							VersionUtils.playError(p);
						}
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new PlaylistGui(PlaylistGui.getPage(e.getInventory()) - 1, PlaylistGui.getPlaylist(e.getInventory()), p).getInventory());
					
						VersionUtils.playPop(p);
					}
				}
			
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 3) {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						p.openInventory(new PlaylistGui(PlaylistGui.getPage(e.getInventory()) + 1, PlaylistGui.getPlaylist(e.getInventory()), p).getInventory());
					
						VersionUtils.playPop(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 5) {
					p.openInventory(new MusicGui(1, p, "").getInventory());
						
					VersionUtils.playPop(p);
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 0 && InventoryUtils.y(e.getRawSlot()) == 5) {
					Playlist playlist = SimplyMusic.inst().getSessionManager().getSession(p).getPlaylist(PlaylistGui.getPlaylist(e.getInventory()));
					
					if(!playlist.getSongs().isEmpty()) {
						SimplyMusic.inst().getSongManager().playList(p, playlist);
						
						p.sendMessage(SimplyMusic.inst().getSongManager().nowPlaying(playlist.getSongs().get(0)));
						VersionUtils.playSuccess(p);
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("eplaylist"));
						VersionUtils.playError(p);
					}
				}
				
				if(InventoryUtils.x(e.getRawSlot()) == 8 && InventoryUtils.y(e.getRawSlot()) == 5) {
					if(p.hasPermission(CommandPermissions.STOP.toString())) {
						SimplyMusic.inst().getSongManager().stop(p);
						
						VersionUtils.playSuccess(p);
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.GREEN + Lang.translate("stopped"));
						
						shouldRefresh = true;
					}else {
						p.sendMessage(Lang.translate("title_prefix") + ChatColor.RED + Lang.translate("nepermissions"));
						VersionUtils.playError(p);
					}
				}
			}
			
			if(shouldRefresh) p.openInventory(new PlaylistGui(PlaylistGui.getPage(e.getInventory()), PlaylistGui.getPlaylist(e.getInventory()), p).getInventory());
		}
		
		if(PlaylistSelectorGui.isChooseGui(e.getInventory())) {
			e.setCancelled(true);
			
			if(e.getCurrentItem() != null) {
				if(InventoryUtils.x(e.getRawSlot()) == 4 && InventoryUtils.y(e.getRawSlot()) == 2) {
					p.openInventory(new MusicGui(1, p, "").getInventory());
					
					VersionUtils.playPop(p);
				}
				
				if(InventoryUtils.y(e.getRawSlot()) == 1) {
					int playlist = e.getCurrentItem().getAmount() - 1;
					p.openInventory(new PlaylistGui(1, playlist, p).getInventory());
					
					VersionUtils.playPop(p);
				}
			}
			
		}
	}
	
}
